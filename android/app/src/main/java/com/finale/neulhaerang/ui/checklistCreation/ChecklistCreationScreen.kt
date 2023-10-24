package com.finale.neulhaerang.ui.checklistCreation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChecklistCreationScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(Modifier.fillMaxWidth())
        }
    ) {
        Content(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(text = "top bar")
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
