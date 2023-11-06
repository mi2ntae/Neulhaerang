package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.LoginReqDto
import com.finale.neulhaerang.data.model.request.RefreshTokenReqDto
import com.finale.neulhaerang.data.model.response.LoginResDto
import com.finale.neulhaerang.data.model.response.RefreshTokenResDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("$BASE/check")
    suspend fun postCheck(): ResponseResult<String>

    @POST("$BASE/login")
    suspend fun login(@Body loginReqDto: LoginReqDto): ResponseResult<LoginResDto>

    @POST("$BASE/refresh")
    suspend fun refresh(@Body refreshTokenResDto: RefreshTokenReqDto): ResponseResult<RefreshTokenResDto>

    companion object {
        private const val BASE = "auth"
        val instance: AuthApi by lazy { Api.instance.create(AuthApi::class.java) }
    }
}