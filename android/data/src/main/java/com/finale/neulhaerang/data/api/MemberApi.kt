package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.response.MemberStatusResDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.GET
import retrofit2.http.Path

interface MemberApi {
    @GET("$BASE/status/{memberId}")
    suspend fun getMemberStatus(@Path("memberId") memberId: Long): ResponseResult<MemberStatusResDto>

    companion object {
        private const val BASE = "member"
        val instance: MemberApi by lazy { Api.instance.create(MemberApi::class.java) }
    }
}