package com.finale.neulhaerang.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    fun postCheck(): Call<String>

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

        val instance = create()
    }
}

/**
 * 테스트를 위한 오브젝트
 * auth/check api가 string을 반환해서 사용함
 */
object APIsPostCheck {
    fun postCheck() {
        val postCheck = APIs.instance.postCheck()
        postCheck.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                println(response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println(t.toString())
            }
        })
    }
}
