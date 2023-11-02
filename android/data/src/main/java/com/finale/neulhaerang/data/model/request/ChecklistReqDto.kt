package com.finale.neulhaerang.data.model.request

import java.time.LocalDateTime
import java.time.LocalTime

/**
 * 루틴 생성 request dto
 * @property content 루틴 이름
 * @property repeated 반복 요일(월부터 시작)
 * @property alarm 알림 여부
 * @property alarmTime 알림 시간(HH:mm)
 * @property statType 올릴 스탯(String)
 */
data class RoutineReqDto(
    val content: String,
    val repeated: List<Boolean>,
    val alarm: Boolean,
    val alarmTime: LocalTime,
    val statType: String,
)

/**
 * 투두 생성 request dto
 * @property content 투두 이름
 * @property statType 올릴 스탯(String)
 * @property todoDate 투두 기한(yyyy-MM-dd HH:mm)
 */
data class TodoReqDto(
    val content: String,
    val statType: String,
    val todoDate: LocalDateTime,
)