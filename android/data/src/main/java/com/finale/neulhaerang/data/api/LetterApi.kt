package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface LetterApi {
    @GET("$BASE/")
    suspend fun getLetter(@Query("date") date: LocalDate): ResponseResult<String>

    companion object {
        private const val BASE = "letter"
        val instance: LetterApi by lazy { Api.instance.create(LetterApi::class.java) }
    }
}