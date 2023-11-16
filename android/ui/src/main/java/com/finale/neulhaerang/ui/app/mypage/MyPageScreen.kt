package com.finale.neulhaerang.ui.app.mypage
import com.unity3d.player.UnityPlayerActivity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

/**
 *  MyPageScreen
 *  마이페이지를 나타내는 화면입니다.
 *  여러 컴포즈를 가져와서 화면을 구성합니다.
 *
 */
@Composable
fun MyPageScreen(navController: NavHostController, getResult: ActivityResultLauncher<Intent>) {
    val mContext = LocalContext.current
    LaunchedEffect(key1 = true) {
        getResult.launch(Intent(mContext, UnityPlayerActivity::class.java))
        navController.popBackStack()
    }
}