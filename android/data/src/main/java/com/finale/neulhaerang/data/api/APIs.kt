package com.finale.neulhaerang.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.POST


/**
 * retrofit2를 사용한 API 서비스
 * companion object (static)의 instance로 사용(싱글톤)
 */
interface APIs {
    @POST("auth/check")
    suspend fun postCheck(): String

    companion object {
        private const val BASE_URL = "http://k9a502.p.ssafy.io:8080/"
        private val gson: Gson = GsonBuilder().setLenient().create()
        private fun create(): APIs {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create()
        }

        // 처음 instance를 사용할 때 초기화
        val instance by lazy { create() }
    }
}
