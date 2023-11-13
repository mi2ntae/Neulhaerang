package com.finale.neulhaerang.data.api

import com.finale.neulhaerang.data.model.request.AroundMemberCharacterReqDto
import com.finale.neulhaerang.data.model.response.AroundMemberCharacterResDto
import com.finale.neulhaerang.data.util.ResponseResult
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ArApi {
    @PATCH("$BASE/around")
    suspend fun changeGeo(@Body changeGeoReqDto: AroundMemberCharacterReqDto): ResponseResult<List<AroundMemberCharacterResDto>>

    companion object {
        private const val BASE = "ar"
        val instance: ArApi by lazy { Api.instance.create(ArApi::class.java) }
    }
}