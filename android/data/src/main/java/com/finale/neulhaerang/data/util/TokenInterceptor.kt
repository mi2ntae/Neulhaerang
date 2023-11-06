package com.finale.neulhaerang.data.util

import android.util.Log
import com.finale.neulhaerang.data.DataStoreApplication
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import java.io.IOException

private const val LOGIN_URL = "http://k9a502.p.ssafy.io/api/auth/login"
private const val REISSUE_URL = "http://k9a502.p.ssafy.io/api/auth/refresh"
private const val WATCH_LOGIN_URL = "http://dev.paymong.com:8080/api/auth/login/watch"

class AccessTokenInterceptor : Interceptor {
    @OptIn(DelicateCoroutinesApi::class)
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val url = chain.request().url.toString()
        Log.d("heejeong", "url : $url, request : ${chain.request()}")
        lateinit var refreshToken: String
        lateinit var accessToken: String
        runBlocking {
            GlobalScope.launch {
                refreshToken =
                    DataStoreApplication.getInstance().getDataStore().getRefreshToken()
                        .firstOrNull()
                        .toString()
                accessToken =
                    DataStoreApplication.getInstance().getDataStore().getAccessToken().firstOrNull()
                        .toString()
            }.join()
        }
        // 재발급 경우
        if (REISSUE_URL == url) {
            return if ("" != refreshToken) {
                val token = "Bearer $refreshToken"
                val newRequest = chain.request().newBuilder()
                    .addHeader("RefreshToken", token)
                    .build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(chain.request())
            }
        }
        // 앱 로그인 경우
        else if (LOGIN_URL == url) {
            return chain.proceed(chain.request())
        }
        // 와치 로그인 경우
        else if (WATCH_LOGIN_URL == url) {
            return chain.proceed(chain.request())
        }
        // 일반 요청 경우
        else {
            return if ("" != accessToken) {
                val token = "Bearer $accessToken"
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(chain.request())
            }
        }
    }
}