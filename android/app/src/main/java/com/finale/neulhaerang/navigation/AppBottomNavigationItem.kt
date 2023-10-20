package com.finale.neulhaerang.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.finale.neulhaerang.ui.main.MainScreen
import com.finale.neulhaerang.ui.mypage.MyPageScreen
import com.finale.neulhaerang.ui.social.SocialScreen


/**
 * 네비게이션을 위한 Item
 *
 * @sample AppBottomNavigationItem.values()를 통해 순회
 */
enum class AppBottomNavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit
) {
    Main("메인", "main", Icons.Rounded.Home, { MainScreen() }),
    MyPage("마이페이지", "mypage", Icons.Rounded.Person, { MyPageScreen() }),
    Social(
        "소셜",
        "social",
        Icons.Rounded.LocationOn,
        { SocialScreen() }),
}
