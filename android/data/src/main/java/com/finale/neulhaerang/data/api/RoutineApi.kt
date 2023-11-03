package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.RoutineReqDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface RoutineApi {
    @GET("${BASE}/")
    suspend fun getRoutine(@Query("date") date: LocalDate): ResponseResult<List<Any>> // TODO 반환 타입 정의 후 Any 바꾸기

    @POST("$BASE/")
    suspend fun postRoutine(@Body routineReqDto: RoutineReqDto): ResponseResult<Any>

    @PATCH("${BASE}/status")
    suspend fun deleteRoutine(@Body request: Any): ResponseResult<Any> // TODO  루틴 삭제 request 정의 후 body 붙이기

    @PATCH("${BASE}/{routineId}")
    suspend fun modifyRoutine(
        @Path("routineId") routineId: Long, @Body request: RoutineReqDto,
    ): ResponseResult<Any>

    @PATCH("${BASE}/check/{dailyRoutineId}")
    suspend fun completeRoutine(@Path("dailyRoutineId") dailyRoutineId: Long): ResponseResult<Any>

    companion object {
        private const val BASE = "routine"
        val instance: RoutineApi by lazy { Api.instance.create(RoutineApi::class.java) }
    }
}