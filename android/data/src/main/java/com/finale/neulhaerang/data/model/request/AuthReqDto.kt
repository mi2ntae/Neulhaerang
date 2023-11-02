package com.finale.neulhaerang.data.model.request

data class LoginReqDto(
    val provider: String,
    val accessToken: String,
    val deviceToken: String,
)