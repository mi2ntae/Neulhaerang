package com.finale.neulhaerang.data.model.response

import java.time.LocalTime

/**
 * 멤버 캐릭터 정보 조회
 * @param memberId
 * @param backpack
 * @param glasses
 * @param hat 완료 여부
 * @param scarf
 */
data class AroundMemberCharacterResDto(
    val memberId: Long = 0,
    val backpack: Int,
    val glasses: Int,
    val hat: Int,
    val scarf: Int
)