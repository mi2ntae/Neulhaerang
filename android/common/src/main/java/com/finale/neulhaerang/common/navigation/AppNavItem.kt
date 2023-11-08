package com.finale.neulhaerang.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 네비게이션을 위한 Item
 * 모든 네비게이션 정보를 추가
 * @param icon : nullable, ImageVector 형식
 */
enum class AppNavItem(
    val description: String,
    val route: String,
    val icon: ImageVector?,
) {
    Login("로그인", "login", null),
    Main("메인", "main", Icons.Rounded.Home),
    MyPage("마이페이지", "mypage", Icons.Rounded.Person),
    Social("소셜", "social", Icons.Rounded.LocationOn),
    CheckListCreation("체크리스트 생성", "checklist_creation", null),
    CheckListModify("체크리스트 수정", "checklist_creation", null),
    Setting("설정", "setting", Icons.Rounded.Settings)
}

/**
 * 하단 네비게이션 아이템을 위한 리스트
 * 메인, 마이페이지, 소셜(AR)의 3가지
 */
val AppBottomAppNavItems = listOf<AppNavItem>(AppNavItem.Main, AppNavItem.MyPage, AppNavItem.Social)
