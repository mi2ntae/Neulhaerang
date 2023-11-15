package com.finale.neulhaerang.domain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.finale.neulhaerang.common.Stat
import com.finale.neulhaerang.common.message.ErrorMessage
import com.finale.neulhaerang.common.message.ValidationMessage
import com.finale.neulhaerang.data.CheckList
import com.finale.neulhaerang.data.Routine
import com.finale.neulhaerang.data.Todo
import com.finale.neulhaerang.data.api.RoutineApi
import com.finale.neulhaerang.data.api.TodoApi
import com.finale.neulhaerang.data.model.request.RoutineDeleteReqDto
import com.finale.neulhaerang.data.model.request.RoutineReqDto
import com.finale.neulhaerang.data.model.request.TodoReqDto
import com.finale.neulhaerang.data.util.ResponseResult
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class CheckListModifyViewModel(checkList: CheckList, selectedDate: LocalDate) : ViewModel() {
    class Factory(private val checkList: CheckList, private val selectedDate: LocalDate) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // modelClass가 원하는 모델을 상속받았는지 확인
            if (modelClass.isAssignableFrom(CheckListModifyViewModel::class.java)) {
                return CheckListModifyViewModel(
                    checkList = checkList,
                    selectedDate = selectedDate
                ) as T
            }
            // 상속이 되지 않았다면 IllegalArgumentException
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

    val routine = checkList is Routine

    private var todoId: Long = 0
    private var routineId: Long = 0
    private var dailyRoutineId: Long = 0

    private val _content = mutableStateOf(checkList.content)
    private val _stat = mutableStateOf(checkList.statType)
    private val _repeat = mutableStateOf(List(7) { _ -> false })
    private val _dateTime = mutableStateOf(LocalDateTime.now())
    private val _alarm = mutableStateOf(checkList.alarm)
    private val _stopRoutine = mutableStateOf(false)

    init {
        if (checkList is Routine) {
            routineId = checkList.routineId
            dailyRoutineId = checkList.dailyRoutineId
            _repeat.value = checkList.repeated
        } else if (checkList is Todo) {
            todoId = checkList.todoId
        }
        _dateTime.value = checkList.alarmTime?.atDate(selectedDate) ?: LocalDateTime.now()
    }

    val content: String
        get() = _content.value
    val stat: Stat
        get() = _stat.value
    val repeat: List<Boolean>
        get() = _repeat.value
    val dateTime: LocalDateTime
        get() = _dateTime.value
    val alarm: Boolean
        get() = _alarm.value
    val stopRoutine: Boolean
        get() = _stopRoutine.value

    val timeHour: Int
        get() = _dateTime.value.hour
    val timeMinute: Int
        get() = _dateTime.value.minute

    // state change
    fun changeContent(input: String) {
        _content.value = input
    }

    fun changeStat(input: Stat) {
        _stat.value = input
    }

    fun clearContent() {
        _content.value = ""
    }

    fun changeRepeat(index: Int) {
        _repeat.value.toMutableList().also { it[index] = !it[index] }.also { _repeat.value = it }
    }

    fun changeDate(dateMillis: Long) {
        _dateTime.value.with(
            Instant.ofEpochMilli(dateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
        ).also { _dateTime.value = it }
    }

    fun changeTime(hour: Int, minute: Int) {
        _dateTime.value.withHour(hour).withMinute(minute).also { _dateTime.value = it }
    }

    fun changeAlarm(input: Boolean) {
        _alarm.value = input
    }

    fun setStopRoutine(input: Boolean) {
        _stopRoutine.value = input
    }

    // business logic
    private fun validateValues(): String? {
        return when {
            content.isEmpty() -> ValidationMessage.NoContent.message
            routine && repeat.all { !it } -> ValidationMessage.NoRepeat.message
            !routine && dateTime <= LocalDateTime.now() -> ValidationMessage.PastTime.message
            else -> null
        }
    }

    private fun makeRequest(): Any {
        return if (routine) {
            RoutineReqDto(
                content,
                repeat,
                alarm,
                dateTime.toLocalTime(),
                stat.statName
            )
        } else {
            TodoReqDto(
                content,
                alarm,
                stat.statName,
                dateTime
            )
        }
    }

    suspend fun modifyCheckList(): String? {
        // 값 확인
        validateValues().let { if (it !== null) return it }
        // api 통신
        makeRequest().let {
            when (it) {
                is RoutineReqDto -> RoutineApi.instance.modifyRoutine(routineId, it)
                is TodoReqDto -> TodoApi.instance.modifyTodo(todoId, it)
                else -> ResponseResult.Failure(0, "fail")
            }
        }.onSuccess {
            // 성공
            return null
        }.onFailure { (code, _, _) ->
            // 실패
            return when (code) {
                400 -> ErrorMessage.Code400.message
                403 -> ErrorMessage.Code403.message
                500 -> ErrorMessage.Code500.message
                else -> ErrorMessage.CodeUnknown.message
            }
        }
        return ErrorMessage.CodeUnknown.message
    }

    suspend fun deleteCheckList(): String? {
        if (routine) {
            val request = RoutineDeleteReqDto(dailyRoutineId, routineId, stopRoutine)
            RoutineApi.instance.deleteRoutine(request)
        } else {
            TodoApi.instance.deleteTodo(todoId)
        }.onSuccess {
            // 성공
            return null
        }.onFailure { (code, _, _) ->
            // 실패
            return when (code) {
                400 -> ErrorMessage.Code400.message
                403 -> ErrorMessage.Code403.message
                500 -> ErrorMessage.Code500.message
                else -> ErrorMessage.CodeUnknown.message
            }
        }
        return ErrorMessage.CodeUnknown.message
    }
}