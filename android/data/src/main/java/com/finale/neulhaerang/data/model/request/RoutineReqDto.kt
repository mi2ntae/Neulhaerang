package com.finale.neulhaerang.data.model.request

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
