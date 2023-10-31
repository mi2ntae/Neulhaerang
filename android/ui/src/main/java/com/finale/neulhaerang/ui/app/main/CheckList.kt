package com.finale.neulhaerang.ui.app.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finale.neulhaerang.data.CheckList
import com.finale.neulhaerang.ui.theme.Typography
import java.time.LocalDate


@Composable
fun CheckList(selectedDate: LocalDate) {
    val checklists = listOf<CheckList>(
        CheckList("안녕", true),
        CheckList("물 8잔 마시기", false),
        CheckList("CS 스터디 - JAVASCRIPT", false),
        CheckList("현대오토에버 이력서 작성", false),
        CheckList("CS 스터디 - React", false),
        CheckList("CS 스터디 - Kotlin", false),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState(0))
            .padding(16.dp),
    ) {
        Text(text = selectedDate.toString())
        Routine(checklists)
        Spacer(modifier = Modifier.height(16.dp))
        TodoList(checklists)
    }
}


@Composable
fun Routine(routines: List<CheckList>) {
    Text(text = "Routine", style = Typography.bodyLarge)
    Column(
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        routines.forEach { item ->
            CheckListItem(item)
        }
    }
}


@Composable
fun TodoList(todolist: List<CheckList>) {
    Text(text = "To do", style = Typography.bodyLarge)
    Column(
//        modifier = Modifier.fillMaxSize()
//        contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        todolist.forEach { item ->
            CheckListItem(item)
        }
    }
}

@Composable
fun CheckListItem(item: CheckList) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = { !item.isCompleted },
        )
        Text(
            text = item.content,
            style = Typography.bodyLarge.merge(
                TextStyle(
                    lineHeight = 30.sp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.FirstLineTop
                    )
                )
            )
        )
    }

}