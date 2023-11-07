package com.finale.neulhaerang.data.util

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseResultCall<T>(
    private val call: Call<T>,
) : Call<ResponseResult<T>> {
    override fun clone(): Call<ResponseResult<T>> = ResponseResultCall(call.clone())

    override fun execute(): Response<ResponseResult<T>> {
        throw UnsupportedOperationException("ResponseResultCall does not support execute")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel(): Unit = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    override fun enqueue(callback: Callback<ResponseResult<T>>) {
        val TAG = "ResponseResultCall"

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
//                val message = response.message()
                val error = response.errorBody()
                val code = response.code()

                if (response.isSuccessful) {
                    // 성공
                    callback.onResponse(
                        this@ResponseResultCall,
                        Response.success(ResponseResult.Success(code, body))
                    )
                } else {
                    // 실패
                    val errorBody = error!!.string()
                    callback.onResponse(
                        this@ResponseResultCall,
                        Response.success(ResponseResult.Failure(code, errorBody, null))
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                Response.success(ResponseResult.Failure(0, t.message ?: "null", t))
            }
        })
    }
}