package com.finale.neulhaerang.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.common.navigation.AppNavItem
import com.finale.neulhaerang.ui.app.checklistCreation.ChecklistCreationScreen
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
fun App() {
    val navController = rememberNavController()

    NeulHaeRangTheme {
        NavHost(
            navController = navController,
            startDestination = AppNavItem.Login.route,
            modifier = Modifier.fillMaxSize(),
        ) {
            AppNavItem.values().forEach { item ->
                when (item) {
                    AppNavItem.Login -> composable(item.route) {
                        LoginScreen(navController = navController)
                    }

                    AppNavItem.Main -> composable(item.route) {
                        MainScreen(navController = navController)
                    }

                    AppNavItem.MyPage -> composable(item.route) {
                        MyPageScreen(navController = navController)
                    }

                    AppNavItem.Social -> composable(item.route) {
                        SocialScreen()
                    }

                    AppNavItem.ChecklistCreation -> composable(item.route) {
                        ChecklistCreationScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    App()
}
