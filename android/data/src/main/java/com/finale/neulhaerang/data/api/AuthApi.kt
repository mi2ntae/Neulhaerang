package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.LoginReqDto
import com.finale.neulhaerang.data.model.response.LoginResDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/check")
    suspend fun postCheck(): ResponseResult<String>

    @POST("auth/login")
    suspend fun login(@Body loginReqDto: LoginReqDto): ResponseResult<LoginResDto>

    companion object {
        val instance: AuthApi by lazy { Api.instance.create(AuthApi::class.java) }
    }
}