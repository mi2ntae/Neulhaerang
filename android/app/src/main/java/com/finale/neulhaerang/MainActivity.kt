package com.finale.neulhaerang


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.finale.neulhaerang.ui.app.App
import com.kakao.sdk.common.util.Utility

/**
 * 메인 엑티비티
 * 푸시 알림이나 기기 연결 등의 설정이 들어감
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackOnPressed()
            App()
        }
//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)
    }
}

/*
 * 뒤로 가기 두 번 눌렀을 때 앱 종료
 */
@Composable
fun BackOnPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 1000L) {
            // 앱 종료
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
