package com.finale.neulhaerang.ui.app.main

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.finale.neulhaerang.common.message.BlockMessage
import com.finale.neulhaerang.common.navigation.AppNavItem
import com.finale.neulhaerang.data.CheckList
import com.finale.neulhaerang.data.Routine
import com.finale.neulhaerang.data.Todo
import com.finale.neulhaerang.ui.R
import com.finale.neulhaerang.ui.theme.Typography
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CheckList(
    navController: NavHostController,
    routineList: List<Routine>,
    todoList: List<Todo>,
    selectedDate: LocalDate,
    checkCheckList: suspend (CheckList) -> String?,
) {
    val scope = rememberCoroutineScope()
    val (alert, setAlert) = remember { mutableStateOf(false) }
    val (message, setMessage) = remember { mutableStateOf("") }

    val modifyCheckList = { type: String, index: Int ->
        if (selectedDate < LocalDate.now()) {
            setMessage(BlockMessage.PastModifyBlock.message)
            setAlert(true)
        } else
            navController.navigate("${AppNavItem.CheckListModify.route}/$type/$index")
    }

    fun checkCheckListFun(checkList: CheckList) {
        scope.launch {
            val msg = checkCheckList(checkList)
            if (!msg.isNullOrBlank()) {
                setMessage(msg)
                setAlert(true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState(0))
            .padding(16.dp),
    ) {
        Routine(routineList, modifyCheckList) { checkCheckListFun(it) }
        Spacer(modifier = Modifier.height(16.dp))
        TodoList(todoList, modifyCheckList) { checkCheckListFun(it) }
    }

    if (alert) {
        AlertDialog(
            onDismissRequest = { setAlert(false) },
            confirmButton = {
                Button(onClick = { setAlert(false) }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            text = {
                Text(text = message)
            }
        )
    }
}


@Composable
fun Routine(
    routines: List<CheckList>,
    modifyCheckList: (String, Int) -> Unit,
    checkCheckList: (CheckList) -> Unit,
) {
    Text(text = "Routine", style = Typography.bodyLarge)
    if (routines.isEmpty()) {
        Text(text = "오늘 할 루틴이 없어요", modifier = Modifier.padding(8.dp))
    } else {
        Column(
            //        verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            routines.forEachIndexed { index, item ->
                CheckListItem(index, item, modifyCheckList, checkCheckList)
            }
        }
    }
}


@Composable
fun TodoList(
    todolist: List<CheckList>,
    modifyCheckList: (String, Int) -> Unit,
    checkCheckList: (CheckList) -> Unit,
) {
    Text(text = "To do", style = Typography.bodyLarge)
    if (todolist.isEmpty()) {
        Text(text = "오늘의 할 일이 없어요", modifier = Modifier.padding(8.dp))
    } else {
        Column(
            //        modifier = Modifier.fillMaxSize()
            //        contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
            //        verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            todolist.forEachIndexed { index, item ->
                CheckListItem(index, item, modifyCheckList, checkCheckList)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckListItem(
    index: Int,
    item: CheckList,
    modifyCheckList: (String, Int) -> Unit,
    checkCheckList: (CheckList) -> Unit,
) {
    val type = if (item is Routine) "routine" else "todo"

    Row(
        modifier = Modifier.combinedClickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
            // 길게 누르면 수정
            onLongClick = { modifyCheckList(type, index) },
            onClick = {}),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 체크 박스
        Checkbox(
            checked = item.check,
            onCheckedChange = { checkCheckList(item) },
        )
        Spacer(modifier = Modifier.width(8.dp))
        // 체크리스트 이름
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
        // 알림 여부
        if (item.alarm && item.alarmTime != null) {
            run {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.alarmTime!!.format(DateTimeFormatter.ofPattern("h:mm a")))
            }
        }
    }

}