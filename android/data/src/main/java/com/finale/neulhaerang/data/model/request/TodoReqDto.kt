package com.finale.neulhaerang.data.model.request

import java.time.LocalDateTime

/**
 * 투두 생성 request dto
 * @property content 투두 이름
 * @property alarm 알림 여부
 * @property statType 올릴 스탯(String)
 * @property todoDate 투두 기한(yyyy-MM-dd HH:mm)
 */
data class TodoReqDto(
    val content: String,
    val alarm: Boolean,
    val statType: String,
    val todoDate: LocalDateTime,
)
