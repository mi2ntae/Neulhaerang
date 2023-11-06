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
import androidx.compose.runtime.mutableStateOf
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
import com.finale.neulhaerang.domain.MainScreenViewModel
import com.finale.neulhaerang.ui.app.navigation.NHLNavigationBar
import com.finale.neulhaerang.ui.app.navigation.stackNavigate
import com.finale.neulhaerang.ui.theme.Typography
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun MainScreen(navController: NavHostController) {
    val mainScreenViewModel = viewModel<MainScreenViewModel>()

    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("체크리스트", "우편함")
//    val currentDate = LocalDate.now()
//    val (selectedDate, setDateTime) = remember { mutableStateOf(LocalDateTime.now()) }
    val selectedDate = mainScreenViewModel.selectedDate.value

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
                    0 -> CheckList(selectedDate = selectedDate)
                    1 -> Letter(selectedDate = selectedDate)
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
