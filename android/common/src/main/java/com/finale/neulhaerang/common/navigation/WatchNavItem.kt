package com.finale.neulhaerang.common.navigation

enum class WatchNavItem (
    val description: String,
    val route: String,
){
    Main("메인","main"),
    Stats("능력치","stats"),
    Checklist("체크리스트 조회","checklist")
}