package com.finale.neulhaerang

import android.util.Log
import com.finale.neulhaerang.data.DataStoreApplication
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainApplication : DataStoreApplication() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들
        setDeivceToken()
        //Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setDeivceToken() {
        var deviceId: String
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                deviceId = task.result.toString()
                Log.i("heejeong", "deviceId $deviceId")
                GlobalScope.launch {
                getDataStore()
                    .setDeviceToken(deviceId)}
            }
        }
    }
}