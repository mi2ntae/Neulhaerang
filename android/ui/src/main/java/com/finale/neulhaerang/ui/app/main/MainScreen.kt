package com.finale.neulhaerang.ui.app.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.common.navigation.AppNavItem
import com.finale.neulhaerang.data.CheckList
import com.finale.neulhaerang.domain.MainScreenViewModel
import com.finale.neulhaerang.ui.app.navigation.NHLNavigationBar
import com.finale.neulhaerang.ui.app.navigation.stackNavigate
import com.finale.neulhaerang.ui.theme.Typography

@Composable
fun MainScreen(navController: NavHostController) {
    val mainScreenViewModel = viewModel<MainScreenViewModel>()

    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("체크리스트", "우편함")
//    val currentDate = LocalDate.now()
//    val (selectedDate, setDateTime) = remember { mutableStateOf(LocalDateTime.now()) }

    // TODO API 연결
    mainScreenViewModel.initCheckList(
        listOf(
            CheckList("안녕", true),
            CheckList("물 8잔 마시기", false),
        ),
        listOf(
            CheckList("CS 스터디 - JAVASCRIPT", false),
            CheckList("현대오토에버 이력서 작성", false),
            CheckList("CS 스터디 - React", false),
            CheckList("CS 스터디 - Kotlin", false),
        )
    )
    // TODO API 연결
    mainScreenViewModel.setLetterText("안녕, 싸피야!\n" + "\n" + "오늘은 내가 한 일들을 알려줄게. 아침에는 8시에 일어났어. 새로운 하루가 시작되는 건 항상 기대돼.\n" + "\n" + "오전에는 SSAFY 수업을 들었어. 오늘은 좀 어려운 프로그래밍 과제를 풀어봤어. 물론 동료들과 함께 협력하면서 문제를 해결했지. \uD83D\uDCAA\n" + "\n" + "점심 때는 친구들을 만나서 곱창전골을 먹었어. 맛있게 먹고 좋은 대화를 나누는 건 항상 즐거운 시간이야.\n" + "\n" + "양치하고 머리 감기도 잊지 않았어. 건강하게 지내려고 노력하고 있어. \uD83E\uDDB7 \uD83D\uDC87\n" + "\n" + "마지막으로, 1시에 잠자리에 들었어. 하루 동안 열심히 노력했지만, 늦게 자는 건 힘들어. \uD83D\uDE34\n" + "\n" + "앞으로도 같이 좋은 순간을 만들고 함께 성장해보자. 싸피야, 내일은 더 좋은 날이 오기를 기대해! \uD83D\uDE0A\uD83C\uDF1F")

    Scaffold(bottomBar = { NHLNavigationBar(navController = navController) },
        floatingActionButton = {
            ChecklistCreationButton(navController = navController)
        }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            AppHeader(navController = navController)
            StatusBar()
            Calendar()
//                currentDate = currentDate,
//                selectedDate = selectedDate.toLocalDate(),
//                setDateTime = setDateTime
//            )
            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = tabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(text = { Text(title) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index })
                    }
                }
                when (tabIndex) {
                    0 -> CheckList()
                    1 -> Letter()
                }
            }
        }
    }
}

@Composable
fun ChecklistCreationButton(navController: NavHostController) {
    FloatingActionButton(onClick = { navController.stackNavigate(AppNavItem.ChecklistCreation.route) }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = AppNavItem.ChecklistCreation.description,
            modifier = Modifier
        )
    }
}

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "늘해랑", style = Typography.titleLarge)
        TextButton(onClick = { navController.stackNavigate(AppNavItem.Setting.route) }) {
            Text(
                text = "설정",
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.labelLarge
            )
        }
    }

}

@Preview
@Composable
fun Preview() {
    MainScreen(navController = rememberNavController())
}
