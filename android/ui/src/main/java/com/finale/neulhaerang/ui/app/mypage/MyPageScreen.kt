package com.finale.neulhaerang.ui.app.mypage

import BoxStat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("능력치", "의상실", "칭호")

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { NHLNavigationBar(navController = navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MyPageHeader()
            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = tabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(text = { Text(title) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index })
                    }
                }
                when (tabIndex) {
                    0 -> StatPage()
                    1 -> ClosetPage()
                    2 -> TitlePage()
                }
            }
        }
    }
}

@Composable
fun StatPage(){
    /*TODO*/
    // radar chart 구현해야함
    // HexagonStat()
    BoxStat("S+", "S", "B+", "A+", "C", "F")
}

@Preview
@Composable
fun Preview() {
    MyPageScreen(navController = rememberNavController())
}
