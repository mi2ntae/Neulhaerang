package com.finale.neulhaerang.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finale.neulhaerang.common.Stat
import com.finale.neulhaerang.data.api.RoutineApi
import com.finale.neulhaerang.data.api.TodoApi
import com.finale.neulhaerang.data.model.request.RoutineReqDto
import com.finale.neulhaerang.data.model.request.TodoReqDto
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class CheckListCreationViewModel() : ViewModel() {
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

    val dateMilli: Long
        get() = _dateTime.value.toInstant(ZoneOffset.UTC).toEpochMilli()
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

    fun makeChecklist() {
        if (_routine.value) {
            // 루틴 생성
            val request = RoutineReqDto(
                content = _content.value,
                repeated = _repeat.value,
                alarm = _alarm.value,
                alarmTime = _dateTime.value.toLocalTime(),
                statType = _stat.value.statName
            )
            Log.d(TAG, "makeChecklist: $request")

            // TODO API 통신
            viewModelScope.launch {
                RoutineApi.instance.postRoutine(request).onSuccess { (code, _) ->
                    Log.d(TAG, "makeChecklist: success $code")
                }.onFailure { (code, message, _) ->
                    Log.d(TAG, "makeChecklist: fail $code\n$message")
                }
            }
        } else {
            // 투두 생성
            val request = TodoReqDto(
                content = _content.value,
                alarm = _alarm.value,
                statType = _stat.value.statName,
                todoDate = _dateTime.value
            )
            Log.d(TAG, "makeChecklist: $request")

            // TODO API 통신
            viewModelScope.launch {
                TodoApi.instance.postTodo(request).onSuccess { (code, _) ->
                    Log.d(TAG, "makeChecklist: success $code")
                }.onFailure { (code, message, _) ->
                    Log.d(TAG, "makeChecklist: fail $code\n$message")
                }
            }
        }
    }
}