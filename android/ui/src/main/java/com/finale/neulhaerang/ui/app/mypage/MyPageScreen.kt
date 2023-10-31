package com.finale.neulhaerang.ui.app.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.ui.app.navigation.NHLNavigationBar

/**
 *  MyPageScreen
 *  마이페이지를 나타내는 화면입니다.
 *  여러 컴포즈를 가져와서 화면을 구성합니다.
 *
 */
@Composable
fun MyPageScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { NHLNavigationBar(navController = navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("마이페이지입니당")
            MyPageHeader()

            /*TODO*/
            // Stat, Closet, Title 을 선택해서 보여줄 수 있는
            // SelectionBar 구현 필요
            MyPageSelectionBar()

            /*TODO*/
            // radar chart 구현해야함
            // HexagonStat()

            /*TODO*/
            // 추후 SelectionBar 완성시 배치
            // BoxStat("S+", "S", "B+", "A+", "C", "F")


            ClosetPage()
            TitlePage()
        }
    }
}

@Preview
@Composable
fun Preview() {
    MyPageScreen(navController = rememberNavController())
}
