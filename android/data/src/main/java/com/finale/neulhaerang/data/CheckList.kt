package com.finale.neulhaerang.data

import com.finale.neulhaerang.common.Stat
import com.finale.neulhaerang.data.model.response.RoutineResDto
import java.time.LocalTime

open class CheckList(
    val alarm: Boolean,
    val alarmTime: LocalTime?,
    var check: Boolean,
    val content: String,
    val statType: Stat,
)

class Routine(
    val dailyRoutineId: Long,
    val routineId: Long,
    alarm: Boolean,
    alarmTime: LocalTime?,
    check: Boolean,
    content: String,
    statType: Stat,
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

fun setStat(statString: String): Stat {
    Stat.values().forEach { if (it.statName == statString) return it }
    return Stat.GodSang
}