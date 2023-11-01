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
