package com.finale.neulhaerang.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.navigation.NavItem
import com.finale.neulhaerang.ui.stackNavigate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Scaffold(
            floatingActionButton = {
                ChecklistCreationButton(navController = navController)
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(text = "메인스크린")
            }
        }
    }
}

@Composable
fun ChecklistCreationButton(navController: NavHostController) {
    FloatingActionButton(onClick = { navController.stackNavigate(NavItem.ChecklistCreation.route) }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = NavItem.ChecklistCreation.description,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun Preview() {
    MainScreen(navController = rememberNavController())
}
