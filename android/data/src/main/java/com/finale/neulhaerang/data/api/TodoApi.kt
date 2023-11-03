package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.TodoReqDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface TodoApi {
    @GET("$BASE/")
    suspend fun getTodo(@Query("date") date: LocalDate): ResponseResult<List<Any>> // TODO 반환 타입 정의 후 Any 바꾸기

    @POST("$BASE/")
    suspend fun postTodo(@Body request: TodoReqDto): ResponseResult<Any>

    @PATCH("$BASE/status/{todoId}")
    suspend fun deleteTodo(@Path("todoId") todoId: Long): ResponseResult<Any>

    @PATCH("$BASE/check/{todoId}")
    suspend fun completeTodo(@Path("todoId") todoId: Long): ResponseResult<Any>

    @PATCH("$BASE/{todoId}")
    suspend fun modifyTodo(
        @Path("todoId") todoId: Long, @Body request: TodoReqDto,
    ): ResponseResult<Any>

    @GET("$BASE/")
    suspend fun getCompleteTodo(@Body request: TodoReqDto): ResponseResult<Int>

    companion object {
        private const val BASE = "todo"
        val instance: TodoApi by lazy { Api.instance.create(TodoApi::class.java) }
    }
}