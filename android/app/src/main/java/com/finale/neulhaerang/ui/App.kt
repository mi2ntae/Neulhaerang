package com.finale.neulhaerang.ui

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
import com.finale.neulhaerang.navigation.BottomNavItems
import com.finale.neulhaerang.navigation.NavItem
import com.finale.neulhaerang.ui.checklistCreation.ChecklistCreationScreen
import com.finale.neulhaerang.ui.main.MainScreen
import com.finale.neulhaerang.ui.mypage.MyPageScreen
import com.finale.neulhaerang.ui.social.SocialScreen
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
                AppNavGraph(
                    navController = navController,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                )
            },
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                    BottomNavItems.forEach {
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
 * 라우트에 해당하는 스크린 지정
 * 스크린에 인자가 필요한 경우 추가
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Main.route,
        modifier = modifier
    ) {
        composable(NavItem.Main.route) {
            MainScreen(navController)
        }
        composable(NavItem.MyPage.route) {
            MyPageScreen()
        }
        composable(NavItem.Social.route) {
            SocialScreen()
        }
        composable(NavItem.ChecklistCreation.route) {
            ChecklistCreationScreen()
        }
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
        launchSingleTop = true
    }
}

@Preview
@Composable
fun Preview() {
    App()
}
