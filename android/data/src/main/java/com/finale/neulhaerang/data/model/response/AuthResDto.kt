package com.finale.neulhaerang.data.model.response

import java.time.LocalDateTime

data class LoginResDto(
    val memberId: Long,
    val nickname: String,
    val accessToken: String,
    val refreshToken: String,
    val expiredTime: LocalDateTime,
)

data class RefreshTokenResDto(
    val accessToken: String,
    val refreshToken: String,
    val expiredTime: LocalDateTime,
)