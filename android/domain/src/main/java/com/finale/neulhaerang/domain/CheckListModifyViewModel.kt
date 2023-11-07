package com.finale.neulhaerang.domain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.finale.neulhaerang.common.Stat
import com.finale.neulhaerang.data.CheckList
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class CheckListModifyViewModel(checkList: CheckList) : ViewModel() {
    class Factory(private val checkList: CheckList) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // modelClass가 원하는 모델을 상속받았는지 확인
            if (modelClass.isAssignableFrom(CheckListModifyViewModel::class.java)) {
                return CheckListModifyViewModel(checkList = checkList) as T
            }
            // 상속이 되지 않았다면 IllegalArgumentException
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

    private val _content = mutableStateOf("")
    private val _stat = mutableStateOf(Stat.GodSang)
    private val _routine = mutableStateOf(false)
    private val _repeat = mutableStateOf(List(7) { _ -> false })
    private val _dateTime = mutableStateOf(LocalDateTime.now())
    private val _alarm = mutableStateOf(false)

    val content: String
        get() = _content.value
    val stat: Stat
        get() = _stat.value
    val routine: Boolean
        get() = _routine.value
    val repeat: List<Boolean>
        get() = _repeat.value
    val dateTime: LocalDateTime
        get() = _dateTime.value
    val alarm: Boolean
        get() = _alarm.value

    val timeHour: Int
        get() = _dateTime.value.hour
    val timeMinute: Int
        get() = _dateTime.value.minute

    fun changeContent(input: String) {
        _content.value = input
    }

    fun changeStat(input: Stat) {
        _stat.value = input
    }

    fun clearContent() {
        _content.value = ""
    }

    fun changeRoutine(input: Boolean) {
        _routine.value = input
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

    fun modifyCheckList() {
        // TODO modify checklist
    }
}