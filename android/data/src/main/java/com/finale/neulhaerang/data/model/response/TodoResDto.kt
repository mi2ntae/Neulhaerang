package com.finale.neulhaerang.data.model.response

import java.time.LocalTime

/**
 * 루틴 조회 응답
 * @param todoId
 * @param content
 * @param check 완료 여부
 * @param alarm
 * @param alarmTime
 * @param statType String
 */
data class TodoResDto(
    val todoId: Long,
    val content: String,
    val check: Boolean = false,
    val alarm: Boolean = false,
    val alarmTime: LocalTime?,// = LocalTime.now(),
    val statType: String = "갓생력",
)