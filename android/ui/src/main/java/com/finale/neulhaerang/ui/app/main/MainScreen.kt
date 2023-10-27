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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.common.navigation.AppNavItem
import com.finale.neulhaerang.ui.app.stackNavigate
import com.finale.neulhaerang.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
//        containerColor = Color(0xFFBE1515),
        floatingActionButton = {
            ChecklistCreationButton(navController = navController)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AppHeader()
            StatusBar()
            DayElement()
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
