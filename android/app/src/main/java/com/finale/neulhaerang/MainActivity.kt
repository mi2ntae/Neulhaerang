package com.finale.neulhaerang


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.finale.neulhaerang.ui.App

/**
 * 메인 엑티비티
 * 푸시 알림이나 기기 연결 등의 설정이 들어감
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
