package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.util.ResponseResult
import com.finale.neulhaerang.data.util.ResponseResultAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * retrofit2를 사용한 API 서비스
 * companion object (static)의 instance 사용(싱글톤)
 */
interface APIs {
    // auth 관련 함수
    @POST("auth/check")
    suspend fun postCheck(): String

    companion object {
        private const val BASE_URL = "http://k9a502.p.ssafy.io/api/"
//        private val gson: Gson = GsonBuilder().setLenient().create()
        private val gson: Gson = GsonBuilder().setLenient().registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter()).create()
        private fun create():Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(ResponseResultAdapter.Factory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        // 처음 instance 사용할 때 초기화
        val instance by lazy { create() }

    }
    class GsonDateFormatAdapter : JsonSerializer<LocalDateTime?>, JsonDeserializer<LocalDateTime?> {
        @Synchronized
        override fun serialize(localDateTime: LocalDateTime?, type: Type?, jsonSerializationContext: JsonSerializationContext?): JsonElement {
//            return JsonPrimitive(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(localDateTime))
            return JsonPrimitive(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(localDateTime))
        }
        @Synchronized
        override fun deserialize(jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext?): LocalDateTime {
//            return LocalDateTime.parse(jsonElement.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            return LocalDateTime.parse(jsonElement.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        }
    }
}
