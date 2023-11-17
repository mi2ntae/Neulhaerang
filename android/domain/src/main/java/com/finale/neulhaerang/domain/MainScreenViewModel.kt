package com.finale.neulhaerang.domain

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.finale.neulhaerang.common.message.BlockMessage
import com.finale.neulhaerang.data.CheckList
import com.finale.neulhaerang.data.DataStoreApplication
import com.finale.neulhaerang.data.Routine
import com.finale.neulhaerang.data.Todo
import com.finale.neulhaerang.data.api.LetterApi
import com.finale.neulhaerang.data.api.MemberApi
import com.finale.neulhaerang.data.api.RoutineApi
import com.finale.neulhaerang.data.api.TodoApi
import com.finale.neulhaerang.data.model.response.RoutineResDto
import com.finale.neulhaerang.data.model.response.TodoDoneResDto
import com.finale.neulhaerang.data.model.response.TodoResDto
import com.finale.neulhaerang.data.util.ResponseResult
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainScreenViewModel : ViewModel() {
    companion object {
        val viewModelStore = ViewModelStore()
        val storeOwner = object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore
                get() = this@Companion.viewModelStore
        }
    }

    private val _memberId = mutableLongStateOf(0)
    private val _indolence = mutableIntStateOf(0)
    private val _tiredness = mutableIntStateOf(0)

    private val _todoDoneList = mutableStateListOf<TodoDoneResDto>()
    private val _beforeYearMonth = mutableStateOf("")

    private val _selectedDateTime = mutableStateOf(LocalDateTime.now())
    private val _routineList = mutableStateListOf<Routine>()
    private val _todoList = mutableStateListOf<Todo>()
    private val _letterText = mutableStateOf("")

    private val _loading = mutableStateOf(false)

    init {
        viewModelScope.launch {
            _memberId.longValue =
                DataStoreApplication.getInstance().getDataStore().getMemberId().firstOrNull() ?: 0
            getMemberStatus()
        }
    }

    // getter
    val indolence: Int
        get() = _indolence.intValue
    val tiredness: Int
        get() = _tiredness.intValue
    val todoDoneList: List<TodoDoneResDto>
        get() = _todoDoneList.toList()
    val beforeYearMonth: String
        get() = _beforeYearMonth.value
    val selectedDate: LocalDate
        get() = _selectedDateTime.value.toLocalDate()
    val routineList: List<Routine>
        get() = _routineList
    val todoList: List<Todo>
        get() = _todoList
    val letterText: String
        get() = _letterText.value
    val loading: Boolean
        get() = _loading.value

    val yearMonth: String
        get() = DateTimeFormatter.ofPattern("yyyy-MM").format(selectedDate)
    val canIndolence: Boolean
        get() = indolence < 70

    // setter
    private fun updateBeforeYearMonth() {
        _beforeYearMonth.value = yearMonth
    }

    private fun setTodoDoneList(todoDoneList: List<TodoDoneResDto>) {
        _todoDoneList.clear()
        todoDoneList.forEach { _todoDoneList.add(it) }
    }

    fun setDateTime(input: LocalDateTime) {
        _selectedDateTime.value = input
        setDataFromDateTime()
    }

    private fun initRoutine(routines: List<RoutineResDto>) {
        _routineList.clear()
        routines.forEach { addRoutine(Routine(it)) }
    }

    private fun initTodo(todos: List<TodoResDto>) {
        _todoList.clear()
        todos.forEach { addTodo(Todo(it)) }
    }

    fun addRoutine(routine: Routine) {
        _routineList.add(routine)
    }

    fun addTodo(todo: Todo) {
        _todoList.add(todo)
    }

    private fun setLetterText(input: String) {
        _letterText.value = input
    }

    // business logic
    private suspend fun updateTodoDoneList() {
        TodoApi.instance.getCompleteTodo(yearMonth).onSuccess { (_, data) ->
            setTodoDoneList(data!!)
            updateBeforeYearMonth()
        }
    }

    private fun setDataFromDateTime() {
        viewModelScope.launch {
            _loading.value = true
            // 완료 퍼센트 가져오기
            if (beforeYearMonth != yearMonth) {
                updateTodoDoneList()
            }
            // 오늘의 체크리스트
            val deferred = listOf(
                async {
                    withContext(Dispatchers.IO) {
                        RoutineApi.instance.getRoutine(selectedDate)
                            .onSuccess { initRoutine(it.data!!) }
                            .onFailure {
                                Log.w(TAG, "setDataFromDateTime: fail ${it.code} ${it.message}")
                            }
                    }
                },
                async {
                    withContext(Dispatchers.IO) {
                        TodoApi.instance.getTodo(selectedDate)
                            .onSuccess { initTodo(it.data!!) }
                            .onFailure {
                                Log.w(TAG, "setDataFromDateTime: fail ${it.code} ${it.message}")
                            }
                    }
                },
                // 오늘의 편지
                async {
                    withContext(Dispatchers.IO) {
                        LetterApi.instance.getLetter(selectedDate).onSuccess {
                            setLetterText(it.data ?: "")
                        }
                    }
                }
            )
            deferred.awaitAll()
            _loading.value = false
        }
    }

    suspend fun checkCheckList(checkList: CheckList): String? {
        if (selectedDate != LocalDate.now()) {
            return BlockMessage.NotTodayBlock.message
        }
        if (!canIndolence) {
            return BlockMessage.IndolenceBlock.message
        }
        when (checkList) {
            is Routine -> RoutineApi.instance.completeRoutine(checkList.dailyRoutineId)
            is Todo -> TodoApi.instance.completeTodo(checkList.todoId)
            else -> ResponseResult.Failure(0, "class type error")
        }.onSuccess {
            // 체크리스트 체크시 완료도 갱신
            updateTodoDoneList()
            setDataFromDateTime()
            return null
        }.onFailure {
            Log.w(TAG, "checkCheckList: 체크 실패")
        }
        return null
    }

    private fun getMemberStatus() {
        if (_memberId.longValue == 0L) return
        viewModelScope.launch {
            MemberApi.instance.getMemberStatus(_memberId.longValue)
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    _indolence.intValue = data.indolence
                    _tiredness.intValue = data.tiredness
                }
            val tiredCount = tiredness.let {
                when {
                    it > 70 -> 0
                    it > 50 -> 1
                    it > 30 -> 2
                    else -> 3
                }
            }
            Log.d(TAG, "tiredCount: $tiredCount")
            DataStoreApplication.getInstance().getDataStore().setTiredCount(tiredCount)
        }
    }

    fun backToMainScreen() {
        getMemberStatus()
        setDataFromDateTime()
    }
}