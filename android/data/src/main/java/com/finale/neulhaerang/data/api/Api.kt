package com.finale.neulhaerang.data.api

import android.util.Log
import com.finale.neulhaerang.data.util.AccessTokenExpireInterceptor
import com.finale.neulhaerang.data.util.AccessTokenInterceptor
import com.finale.neulhaerang.data.util.GsonDateFormatAdapter
import com.finale.neulhaerang.data.util.GsonDateTimeFormatAdapter
import com.finale.neulhaerang.data.util.GsonTimeFormatAdapter
import com.finale.neulhaerang.data.util.NullOnEmptyConverterFactory
import com.finale.neulhaerang.data.util.ResponseResultAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit


/**
 * retrofit2를 사용한 API 서비스
 * companion object (static)의 instance 사용(싱글톤)
 */
interface Api {
    companion object {
        // 서버 주소
        const val BASE_URL = "http://k9a502.p.ssafy.io/api/"
//        const val BASE_URL = "http://10.0.2.2:8080/"

        private const val TIMEOUT_LIMIT = 180L

        // JSON 파싱용 Gson
        private val gson: Gson = GsonBuilder().setLenient()
            .registerTypeAdapter(LocalDateTime::class.java, GsonDateTimeFormatAdapter())
            .registerTypeAdapter(LocalDate::class.java, GsonDateFormatAdapter())
            .registerTypeAdapter(LocalTime::class.java, GsonTimeFormatAdapter())
            .create()

        // 디버깅 인터셉터
        private val debugInterceptor = LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Log.DEBUG)
            .build()

        // OkHttpClient
        private val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
            .addInterceptor(AccessTokenInterceptor())
            .addInterceptor(AccessTokenExpireInterceptor())
            .addInterceptor(debugInterceptor)
            .build()


        private fun create(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(NullOnEmptyConverterFactory.create())
                .addCallAdapterFactory(ResponseResultAdapter.Factory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        // 처음 instance 사용할 때 초기화
        val instance by lazy { create() }

    }
}
