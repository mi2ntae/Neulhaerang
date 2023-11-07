package com.finale.neulhaerang.ui.app

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finale.neulhaerang.common.navigation.AppNavItem
import com.finale.neulhaerang.domain.KakaoAuthViewModel
import com.finale.neulhaerang.ui.app.checklist.CheckListCreationScreen
import com.finale.neulhaerang.ui.app.checklist.CheckListModifyScreen
import com.finale.neulhaerang.ui.app.config.SettingScreen
import com.finale.neulhaerang.ui.app.login.LoginScreen
import com.finale.neulhaerang.ui.app.main.MainScreen
import com.finale.neulhaerang.ui.app.mypage.MyPageScreen
import com.finale.neulhaerang.ui.app.social.SocialScreen
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme

/**
 * 메인 앱
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(getResult: ActivityResultLauncher<Intent>) {
    val viewModel = viewModel<KakaoAuthViewModel>()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState()
    Log.i("KakaoAuthViewModel", "로그인 되었나요? " + isLoggedIn.value)

    NeulHaeRangTheme {
        if (isLoggedIn.value) AppMain(getResult)
        else LoginScreen()
    }
}

@Composable
fun AppMain(getResult: ActivityResultLauncher<Intent>) {
    val navController = rememberNavController()

    NeulHaeRangTheme {
        NavHost(
            navController = navController,
            startDestination = AppNavItem.Main.route,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable(route = AppNavItem.Main.route) {
                MainScreen(navController)
            }
            composable(route = AppNavItem.MyPage.route) {
                MyPageScreen(navController = navController, getResult)
            }
            composable(route = AppNavItem.Social.route) {
                SocialScreen()
            }
            composable(route = AppNavItem.CheckListCreation.route) {
                CheckListCreationScreen(navController = navController)
            }
            composable(
                route = "${AppNavItem.CheckListModify.route}/{type}/{index}",
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("index") { type = NavType.IntType }
                )) { entry ->
                CheckListModifyScreen(
                    navController = navController,
                    entry.arguments?.getString("type"),
                    entry.arguments?.getInt("index")
                )
            }
            composable(route = AppNavItem.Setting.route) {
                SettingScreen(navController = navController)
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
//    App()
}
