package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.AroundMemberCharacterReqDto
import com.finale.neulhaerang.data.model.response.AroundMemberCharacterResDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ArApi {
    @PATCH("$BASE/around")
    suspend fun changeGeo(@Body changeGeoReqDto: AroundMemberCharacterReqDto): ResponseResult<List<AroundMemberCharacterResDto>>

    @GET("$BASE/social")
    suspend fun getAroundMembers(): ResponseResult<List<AroundMemberCharacterResDto>>

    @DELETE("$BASE/around")
    suspend fun deleteGeo(): ResponseResult<Void>

    @POST("$BASE/tag/{memberId}")
    suspend fun sendClickedMember(@Path("memberId") memberId: Long): ResponseResult<Any>

    companion object {
        private const val BASE = "ar"
        val instance: ArApi by lazy { Api.instance.create(ArApi::class.java) }
    }
}