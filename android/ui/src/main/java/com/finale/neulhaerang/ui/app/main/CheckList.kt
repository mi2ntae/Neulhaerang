package com.finale.neulhaerang.ui.app.main

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.finale.neulhaerang.common.navigation.AppNavItem
import com.finale.neulhaerang.data.CheckList
import com.finale.neulhaerang.domain.MainScreenViewModel
import com.finale.neulhaerang.ui.theme.Typography
import java.time.format.DateTimeFormatter


@Composable
fun CheckList(navController: NavHostController) {
    val viewModel = viewModel<MainScreenViewModel>()

    val selectedDate = viewModel.selectedDate
    val routineList = viewModel.routineList
    val todoList = viewModel.todoList

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState(0))
            .padding(16.dp),
    ) {
        Text(text = selectedDate.toString())
        Routine(routineList, navController)
        Spacer(modifier = Modifier.height(16.dp))
        TodoList(todoList, navController)
    }
}


@Composable
fun Routine(routines: List<CheckList>, navController: NavHostController) {
    Text(text = "Routine", style = Typography.bodyLarge)
    Column(
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        routines.forEach { item ->
            CheckListItem(item, navController)
        }
    }
}


@Composable
fun TodoList(todolist: List<CheckList>, navController: NavHostController) {
    Text(text = "To do", style = Typography.bodyLarge)
    Column(
//        modifier = Modifier.fillMaxSize()
//        contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        todolist.forEach { item ->
            CheckListItem(item, navController)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckListItem(item: CheckList, navController: NavHostController) {
    val viewModel = viewModel<MainScreenViewModel>()

    Row(
        modifier = Modifier.combinedClickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
            onLongClick = {
                Log.d("TAG", "CheckListItem: long click")
                navController.navigate(AppNavItem.CheckListModify.route)
            },
            onClick = {}),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.check,
            onCheckedChange = { viewModel.checkCheckList(item) },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.content, style = Typography.bodyLarge.merge(
                TextStyle(
                    lineHeight = 30.sp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.FirstLineTop
                    ),
                    color = if (item.check) Color.Gray else TextStyle.Default.color,
                    textDecoration = if (item.check) TextDecoration.LineThrough else null
                )
            ),
            modifier = Modifier.weight(1f)
        )
        if (item.alarm && item.alarmTime != null) {
            run {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.alarmTime!!.format(DateTimeFormatter.ofPattern("h:mm a")))
            }
        }
    }

}