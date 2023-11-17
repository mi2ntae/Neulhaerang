package com.finale.neulhaerang.data

import com.finale.neulhaerang.common.Stat
import com.finale.neulhaerang.data.model.response.RoutineResDto
import com.finale.neulhaerang.data.model.response.TodoResDto
import java.time.LocalTime

open class CheckList(
    open val alarm: Boolean,
    open val alarmTime: LocalTime?,
    open val check: Boolean,
    open val content: String,
    open val statType: Stat,
)

data class Routine(
    val dailyRoutineId: Long,
    val routineId: Long,
    override val alarm: Boolean,
    override val alarmTime: LocalTime?,
    override val check: Boolean,
    override val content: String,
    override val statType: Stat,
    val repeated: List<Boolean>,
) : CheckList(alarm, alarmTime, check, content, statType) {
    constructor(resDto: RoutineResDto) : this(
        resDto.dailyRoutineId,
        resDto.routineId,
        resDto.alarm,
        resDto.alarmTime,
        resDto.check,
        resDto.content,
        setStat(resDto.statType),
        resDto.repeated
    )
}

data class Todo(
    val todoId: Long,
    override val alarm: Boolean,
    override val alarmTime: LocalTime?,
    override val check: Boolean,
    override val content: String,
    override val statType: Stat,
) : CheckList(alarm, alarmTime, check, content, statType) {
    constructor(resDto: TodoResDto) : this(
        resDto.todoId,
        resDto.alarm,
        resDto.alarmTime,
        resDto.check,
        resDto.content,
        setStat(resDto.statType),
    )
}

fun setStat(statString: String): Stat {
    Stat.values().forEach { if (it.statName == statString) return it }
    return Stat.GodSang
}