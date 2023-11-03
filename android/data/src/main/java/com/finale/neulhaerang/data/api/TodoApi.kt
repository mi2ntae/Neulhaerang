package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.TodoReqDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.Body
import retrofit2.http.POST

interface TodoApi {
    @POST("$BASE/")
    suspend fun postTodo(@Body request: TodoReqDto): ResponseResult<Any>

    companion object {
        private const val BASE = "todo"
        val instance: TodoApi by lazy { Api.instance.create(TodoApi::class.java) }
    }
}