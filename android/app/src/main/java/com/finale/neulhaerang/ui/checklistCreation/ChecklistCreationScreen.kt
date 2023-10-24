package com.finale.neulhaerang.ui.checklistCreation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistCreationScreen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = "체크리스트 작성") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                }
            },
            actions = {
                TextButton(
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "취소", color = MaterialTheme.colorScheme.secondary)
                }
                TextButton(
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "완료", color = MaterialTheme.colorScheme.primary)
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        )
    }) {
        Content(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        )
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            Text(text = "체크리스트 이름")
        }
        Row {
            Text(text = "루틴 여부 스위치")
        }
        RoutineCreation()
        TodoCreation()
        Row {
            Text(text = "시간")
        }
        Row {
            Text(text = "알림")
        }
    }
}

@Composable
fun RoutineCreation(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            Text(text = "반복주기")
        }
    }
}

@Composable
fun TodoCreation(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            Text(text = "날짜")
        }
    }
}

@Preview
@Composable
fun Preview() {
    NeulHaeRangTheme {
        ChecklistCreationScreen(navController = rememberNavController())
    }
}
