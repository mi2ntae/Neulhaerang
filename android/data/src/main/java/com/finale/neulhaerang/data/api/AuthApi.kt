package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.LoginReqDto
import com.finale.neulhaerang.data.model.response.LoginResDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body loginReqDto: LoginReqDto): LoginResDto

    companion object {
        val instance: AuthApi by lazy { APIs.instance.create(AuthApi::class.java) }
    }
}