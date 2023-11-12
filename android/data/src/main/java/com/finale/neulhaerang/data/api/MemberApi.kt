package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.response.MemberStatResDto
import com.finale.neulhaerang.data.model.response.MemberStatusResDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberApi {
    @GET("$BASE/status/{memberId}")
    suspend fun getMemberStatus(@Path("memberId") memberId: Long): ResponseResult<MemberStatusResDto>

    @POST("$BASE/stat")
    suspend fun recordTiredness(@Query("tiredness") tiredness: Int): ResponseResult<Void>

    @GET("$BASE/stat/{memberId}")
    suspend fun getMemberStat(@Path("memberId") memberId: Long): ResponseResult<List<MemberStatResDto>>

    companion object {
        private const val BASE = "member"
        val instance: MemberApi by lazy { Api.instance.create(MemberApi::class.java) }
    }
}