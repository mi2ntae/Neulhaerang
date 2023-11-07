package com.finale.neulhaerang.data.util

import android.content.res.Resources
import android.util.Log
import com.finale.neulhaerang.data.DataStoreApplication
import com.finale.neulhaerang.data.api.Api
import com.finale.neulhaerang.data.api.AuthApi
import com.finale.neulhaerang.data.model.request.RefreshTokenReqDto
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import java.io.IOException
import java.lang.Exception

private const val LOGIN_URL = "${Api.BASE_URL}auth/login"
private const val REISSUE_URL = "${Api.BASE_URL}auth/refresh"
private const val WATCH_LOGIN_URL = "${Api.BASE_URL}auth/login/watch"


/*
* 토큰 추가 클래스
* */
@OptIn(DelicateCoroutinesApi::class)
class AccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val url = chain.request().url.toString()
        Log.d("heejeong", "url : $url, request : ${chain.request()}")
        // 재발급 경우
        if (REISSUE_URL == url) {
            Log.i("heejeong", "토큰 재발급 요청")
//            return if ("" != refreshToken) {
//                val token = "Bearer $refreshToken"
//                val newRequest =
//                    chain.request().newBuilder().addHeader("RefreshToken", token).build()
//                chain.proceed(newRequest)
//            } else {
//                chain.proceed(chain.request())
//            }
            return chain.proceed(chain.request())
        }
        // 앱 로그인 경우
        else if (LOGIN_URL == url) {
            Log.i("heejeong", "로그인 요청")
            return chain.proceed(chain.request())
        }
        // 와치 로그인 경우
        else if (WATCH_LOGIN_URL == url) {
            return chain.proceed(chain.request())
        }
        // 일반 요청 경우
        else {
            Log.i("heejeong", "일반 요청")
            lateinit var accessToken: String
            runBlocking {
                GlobalScope.launch {
                    accessToken = DataStoreApplication.getInstance().getDataStore().getAccessToken()
                        .firstOrNull().toString()
                }.join()
            }
            return if ("" != accessToken) {
                val token = "Bearer $accessToken"
                val newRequest =
                    chain.request().newBuilder().addHeader("Authorization", token).build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(chain.request())
            }
        }
    }
}

/*
* 토큰 재발급 요청 클래스
* */
@OptIn(DelicateCoroutinesApi::class)
class AccessTokenExpireInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val response = chain.proceed(chain.request())
//        val authRepository = AuthRepository()
        // 토큰 만료
        if (response.code == 401) {
            try {
                Log.d("heejeong", "AccessTokenExpireInterceptor")
                var accessToken = ""
                runBlocking {
                    GlobalScope.launch {
                        val refreshToken =
                            DataStoreApplication.getInstance().getDataStore().getRefreshToken()
                                .firstOrNull().toString()
                        val deviceToken =
                            DataStoreApplication.getInstance().getDataStore().getDeviceToken()
                                .firstOrNull().toString()
                        Log.d("heejeong", "refreshToken $refreshToken")
                        Log.d("heejeong", "deviceToken $deviceToken")
                        AuthApi.instance.refresh(
                            RefreshTokenReqDto(
                                deviceToken = deviceToken,
                                refreshToken = refreshToken,
                            )
                        ).onSuccess {
                            // 토큰 재발급 성공 시 기존 요청 재전송
                            checkNotNull(it.data) { throw Exception() }
                            Log.i("heejeong", "Success Reissue ${it.data}")
                            accessToken = it.data.accessToken
                            DataStoreApplication.getInstance().getDataStore()
                                .setRefreshToken(it.data.refreshToken)
                            DataStoreApplication.getInstance().getDataStore()
                                .setAccessToken(accessToken)
                        }.onFailure { Log.d("heejeong", "Fail Reissue") }
                    }.join()
                }
                if (accessToken != "") {
                    val token = "Bearer $accessToken"
                    Log.d("heejeong", "token $token")
                    val newRequest = chain.request().newBuilder().removeHeader("Authorization")
                        .addHeader("Authorization", token).build()
                    Log.i("heejeong", newRequest.toString())
                    return chain.proceed(newRequest)
                }
            }
            // refreshToken 만료
            catch (e: Resources.NotFoundException) {
                runBlocking {
                    GlobalScope.launch {
                        DataStoreApplication.getInstance().getDataStore().clearDataStore()
                    }.join()
                }
            }
        }
        return response
    }
}