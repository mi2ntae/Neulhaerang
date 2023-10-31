package com.finale.neulhaerang.ui.app.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.finale.neulhaerang.common.navigation.AppBottomAppNavItems
import com.finale.neulhaerang.common.navigation.AppNavItem

@Composable
fun NHLNavigationBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()

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
}

/**
 * 최초의 navigation을 위한 custom navigate
 * 시작 네비게이션인 로그인에서 메인스크린으로 넘어가는 함수
 * 메인에서 뒤로갈 때 로그인 화면으로 돌아가지 않게 스택을 비우는 용도
 */
fun NavHostController.initNavigate(route: String) {
    this.navigate(route) {
        popUpTo(this@initNavigate.graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * bottom navigation을 위한 custom navigate
 * 뒤로가기 시 main으로 가도록 되어있음
 */
fun NavHostController.bottomNavigate(route: String) {
    this.navigate(route) {
        this@bottomNavigate.graph.findNode(AppNavItem.Main.route)
            ?.let { popUpTo(it.id) { saveState = true } }
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * 설정, 체크리스트 작성 등 스택 위로 띄우는 네비게이션 용도
 */
fun NavHostController.stackNavigate(route: String) {
    this.navigate(route) {
        saveState()
        launchSingleTop = true
        restoreState = true
    }
}
