package com.finale.neulhaerang.ui.app.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.common.navigation.AppNavItem
import com.finale.neulhaerang.data.Day
import com.finale.neulhaerang.ui.app.stackNavigate
import com.finale.neulhaerang.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val days = listOf<Day>(
        Day("월요일", 80),
        Day("화요일", 100),
        Day("수요일", 30),
        Day("목요일", 50),
        Day("화요일", 100),
        Day("화요일", 100),
        Day("수요일", 30),
        Day("목요일", 50),
        Day("화요일", 100),
        Day("화요일", 100),
        Day("수요일", 30),
        Day("목요일", 50),
        Day("화요일", 100)

    )
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("체크리스트", "우편함")
    Scaffold(
//        containerColor = Color(0xFFBE1515),
        floatingActionButton = {
            ChecklistCreationButton(navController = navController)
        }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AppHeader()
            StatusBar()
//            DayElement(drawable = R.drawable.good, text = R.string.monday)
            DaysRow(days = days)
            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = tabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(text = { Text(title) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }
                when (tabIndex) {
                    0 -> Test()
                    1 -> Text(text = "2")
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
fun AppHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "설정", style = Typography.labelLarge)
    }

}

@Preview
@Composable
fun Preview() {
    MainScreen(navController = rememberNavController())
}

@Composable
fun Test() {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "설정", style = Typography.labelLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
        Text(text = "늘해랑", style = Typography.titleLarge)
    }
}
