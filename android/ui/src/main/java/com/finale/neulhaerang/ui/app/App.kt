package com.finale.neulhaerang.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.common.navigation.AppBottomAppNavItems
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
    val backStackEntry = navController.currentBackStackEntryAsState()
    NeulHaeRangTheme {
        Scaffold(
            content = {
                NavHost(
                    navController = navController,
                    startDestination = AppNavItem.Login.route,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
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
                                MyPageScreen()
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
            },
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                    AppBottomAppNavItems.forEach {
                        val isSelected = it.route == backStackEntry.value?.destination?.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { navController.bottomNavigate(it.route) },
                            icon = {
                                Icon(
                                    imageVector = it.icon as ImageVector,
                                    contentDescription = it.description
                                )
                            },
                            label = { Text(text = it.name, fontWeight = FontWeight.SemiBold) }
                        )
                    }
                }
            },
        )
    }
}

/**
 * bottom navigation을 위한 custom navigate
 * 뒤로가기 시 main으로 가도록 되어있음
 */
fun NavHostController.bottomNavigate(route: String) {
    this.navigate(route) {
        popUpTo(this@bottomNavigate.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.stackNavigate(route: String) {
    this.navigate(route) {
        popUpTo(this@stackNavigate.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
fun Preview() {
    App()
}
