package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.GET

interface LetterApi {
    @GET("$BASE/")
    suspend fun getLetter(): ResponseResult<Any> // TODO 입력 쿼리와 리스폰스 정의

    companion object {
        private const val BASE = "letter"
        val instance: LetterApi by lazy { Api.instance.create(LetterApi::class.java) }
    }
}