package com.finale.neulhaerang.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finale.neulhaerang.common.Stat
import com.finale.neulhaerang.data.api.APIs
import kotlinx.coroutines.launch
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

    val content: State<String> = _content
    val stat: State<Stat> = _stat
    val routine: State<Boolean> = _routine
    val repeat: State<List<Boolean>> = _repeat
    val dateTime: State<LocalDateTime> = _dateTime
    val alarm: State<Boolean> = _alarm

    val dateMilli: Long = _dateTime.value.toInstant(ZoneOffset.UTC).toEpochMilli()
    val timeHour: Int = _dateTime.value.hour
    val timeMinute: Int = _dateTime.value.minute

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

    fun makeChecklist() {
        /*TODO 리퀘스트 엔티티 생성*/
        /*TODO API 통신*/
        viewModelScope.launch { println(APIs.instance.postCheck()) }
    }
}