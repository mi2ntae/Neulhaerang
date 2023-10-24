package com.finale.neulhaerang.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 *  MyPageScreen
 *  마이페이지를 나타내는 화면입니다.
 *  여러 컴포즈를 가져와서 화면을 구성합니다.
 *
 */
@Composable
fun MyPageScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Column {
            Text("마이페이지입니당")
            MyPageHeader()
            MyPageSelectionBar()
            HexagonStat()
            BoxStat()
            ClosetPage()
            TitlePage()
        }
    }
}

@Preview
@Composable
fun Preview() {
    MyPageScreen()
}
