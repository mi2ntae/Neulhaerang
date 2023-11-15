package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.response.MemberTitlesResDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.GET

interface TitleApi {
    @GET("$BASE/")
    suspend fun getTitles(): ResponseResult<List<MemberTitlesResDto>>
    companion object {
        private const val BASE = "title"
        val instance: TitleApi by lazy { Api.instance.create(TitleApi::class.java) }
    }
}