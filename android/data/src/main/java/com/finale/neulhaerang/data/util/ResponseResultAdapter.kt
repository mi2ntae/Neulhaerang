package com.finale.neulhaerang.data.util

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResponseResultAdapter<T>(
    private val type: Type,
) : CallAdapter<T, Call<ResponseResult<T>>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<T>): Call<ResponseResult<T>> = ResponseResultCall(call)

    class Factory : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit,
        ): CallAdapter<*, *>? {
            // returnType 이 Call 로 감싸여 있는지 확인
            if (Call::class.java != getRawType(returnType)) {
                return null
            }
            // returnType 이 제네릭인지 확인
            check(returnType is ParameterizedType) { "returnType must be Parameterized as Call<ResponseResult<T>>" }
            // returnType에서 첫번째 제네릭 인자를 얻는다
            val responseType = getParameterUpperBound(0, returnType)
            // 추출한 제네릭 인자가 ResponseResult 인지 확인
            if (getRawType(responseType) != ResponseResult::class.java) {
                return null
            }
            // 제네릭 확인
            check(responseType is ParameterizedType) { "Response must be parameterized as ResponseResult<T>" }
            // CallAdapter 생성
            val successBodyType = getParameterUpperBound(0, responseType)
            return ResponseResultAdapter<Any>(successBodyType)
        }
    }
}