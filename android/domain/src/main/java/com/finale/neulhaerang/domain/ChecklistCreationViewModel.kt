package com.finale.neulhaerang.domain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.finale.neulhaerang.common.Stat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class ChecklistCreationViewModel() : ViewModel() {
    private val _content = mutableStateOf("")
    private val _stat = mutableStateOf(Stat.GodSang)
    private val _routine = mutableStateOf(false)
    private val _repeat = mutableStateOf(List(7) { _ -> false })
    private val _dateTime = mutableStateOf(LocalDateTime.now())
    private val _alarm = mutableStateOf(false)

    val content = _content
    val stat = _stat
    val routine = _routine
    val repeat = _repeat
    val dateTime = _dateTime
    val alarm = _alarm

    val dateMillis = _dateTime.value.toInstant(ZoneOffset.UTC).toEpochMilli()

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
        _repeat.value = _repeat.value.toMutableList().also { it[index] = !it[index] }
    }

    fun changeDate(dateMillis: Long) {
        _dateTime.value.with(
            Instant.ofEpochMilli(dateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
        )
    }

    fun changeTime(hour: Int, minute: Int) {
        _dateTime.value.withHour(hour).withMinute(minute)
    }

    fun changeAlarm(input: Boolean) {
        _alarm.value = input
    }
}