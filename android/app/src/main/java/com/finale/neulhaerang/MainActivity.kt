package com.finale.neulhaerang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.finale.neulhaerang.ui.app.App
import com.google.firebase.messaging.FirebaseMessaging


/**
 * 메인 엑티비티
 * 푸시 알림이나 기기 연결 등의 설정이 들어감
 */
class MainActivity : ComponentActivity() {
    lateinit var getResult: ActivityResultLauncher<Intent>
    var pageCode by mutableIntStateOf(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                pageCode= it.data?.getStringExtra("pageCode")?.toInt() ?: 0
            }
        }

//        val token = FirebaseMessaging.getInstance().token.result
//        Log.i("heejeong",token)
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.i("heejeong",it)
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            BackOnPressed()
            App(getResult)
        }
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
