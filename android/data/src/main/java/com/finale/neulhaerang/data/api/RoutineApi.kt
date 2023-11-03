package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.RoutineReqDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.Body
import retrofit2.http.POST

interface RoutineApi {
    @POST("$BASE/")
    suspend fun postRoutine(@Body routineReqDto: RoutineReqDto): ResponseResult<Any>

    companion object {
        private const val BASE = "routine"
        val instance: RoutineApi by lazy { Api.instance.create(RoutineApi::class.java) }
    }
}