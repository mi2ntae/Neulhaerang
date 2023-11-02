package com.finale.neulhaerang

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoLogin : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        //Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}