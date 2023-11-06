package com.finale.neulhaerang.domain

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.finale.neulhaerang.data.DataStoreApplication
import com.finale.neulhaerang.data.api.AuthApi
import com.finale.neulhaerang.data.model.request.LoginReqDto
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class KakaoAuthViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val TAG = "KakaoAuthViewModel"
    }

    private val context = application.applicationContext

    val isLoggedIn = MutableStateFlow<Boolean>(false)
    fun kakaoLogin() {
        viewModelScope.launch {
            isLoggedIn.emit(handleKakaoLogin())
            Log.i("heejeong", "!!!" + isLoggedIn.value)
        }
    }

    private suspend fun handleKakaoLogin(): Boolean = suspendCoroutine { continuation ->
        // 로그인 조합 예제

        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                continuation.resume(false)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                viewModelScope.launch {
                    AuthApi.instance.login(
                        LoginReqDto(
                            "kakao",
                            token.accessToken,
                            "testtest"
                        )
                    ).onSuccess { (_, data) ->
                        Log.i("heejeong", data.toString())
//                        DataStoreApplication.getInstance().getDataStore().clearDataStore()
                        if (data != null) {
                            DataStoreApplication.getInstance().getDataStore()
                                .setAccessToken(data.accessToken)
                            DataStoreApplication.getInstance().getDataStore()
                                .setRefreshToken(data.refreshToken)
                        }
                    }.onFailure { (_, message, _) ->
                        Log.e("heejeong", "서버 로그인 실패! %n$message")
                    }
                }
                continuation.resume(true)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

}