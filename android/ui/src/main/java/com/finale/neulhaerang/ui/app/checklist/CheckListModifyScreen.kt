package com.finale.neulhaerang.ui.app.checklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.common.Stat
import com.finale.neulhaerang.data.CheckList
import com.finale.neulhaerang.data.Routine
import com.finale.neulhaerang.domain.CheckListModifyViewModel
import com.finale.neulhaerang.domain.MainScreenViewModel
import com.finale.neulhaerang.ui.R
import com.finale.neulhaerang.ui.app.fragment.NHLTimePicker
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListModifyScreen(navController: NavHostController, type: String?, index: Int?) {
    val viewModel = viewModel<MainScreenViewModel>(MainScreenViewModel.storeOwner)
    val checkList = when (type) {
        "routine" -> viewModel.routineList
        "todo" -> viewModel.todoList
        else -> {
            listOf<CheckList>(
                Routine(
                    0,
                    0,
                    false,
                    null,
                    false,
                    "dd",
                    Stat.GodSang,
                    List(7) { false })
            )
        }
    }[index ?: 0]
    val selectedDate = viewModel.selectedDate

    var showAlertDialog by remember { mutableStateOf(false) }

    if (showAlertDialog) {
        CheckListDeleteDialog(
            navController = navController,
            onDismissRequest = { showAlertDialog = false })
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "체크리스트 수정"
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }
                }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                actions = {
                    TextButton(onClick = { showAlertDialog = true }) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) {
        CheckListModifyContent(
            modifier = Modifier
                .padding(paddingValues = it)
                .padding(all = 16.dp)
                .imePadding()
                .fillMaxSize(), navController = navController,
            checkList = checkList,
            selectedDate = selectedDate,
            viewModel::setDataFromDateTime
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListModifyContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    checkList: CheckList,
    selectedDate: LocalDate,
    setDataFromDateTime: () -> Unit,
) {
    val viewModel =
        viewModel<CheckListModifyViewModel>(
            factory = CheckListModifyViewModel.Factory(
                checkList = checkList,
                selectedDate = selectedDate
            )
        )

    Column(modifier = modifier) {
        CheckListContentInput(
            content = viewModel.content,
            changeContent = viewModel::changeContent,
            clearContent = viewModel::clearContent,
            stat = viewModel.stat,
            changeStat = viewModel::changeStat,
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (viewModel.routine)
            RoutineCreation(
                repeat = viewModel.repeat,
                changeRepeat = viewModel::changeRepeat
            ) else
            TodoCreation(
                dateTime = viewModel.dateTime,
                changeDate = viewModel::changeDate
            )
        CheckListItem(
            name = stringResource(id = R.string.checklist_category_time),
            icon = Icons.Filled.Schedule
        ) {
            var showSheet by remember { mutableStateOf(false) }
            val timePickerState = rememberTimePickerState(
                initialHour = viewModel.timeHour,
                initialMinute = viewModel.timeMinute,
                is24Hour = false
            )

            TextButton(onClick = { showSheet = true }) {
                Text(text = viewModel.dateTime.format(DateTimeFormatter.ofPattern("h:mm a")))
            }
            NHLTimePicker(open = showSheet,
                close = { showSheet = false },
                timePickerState = timePickerState,
                onOk = { viewModel.changeTime(timePickerState.hour, timePickerState.minute) })
        }
        CheckListItem(
            name = stringResource(id = R.string.checklist_category_notice),
            icon = Icons.Filled.Alarm
        ) {
            Switch(checked = viewModel.alarm, onCheckedChange = viewModel::changeAlarm)
        }
        Spacer(modifier = Modifier.weight(weight = 1f))
        Button(
            onClick = {
                viewModel.modifyCheckList()
                setDataFromDateTime()
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.complete),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun CheckListDeleteDialog(
    navController: NavHostController,
    onDismissRequest: () -> Unit,
    viewModel: CheckListModifyViewModel = viewModel(),
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    viewModel.deleteCheckList()
                    onDismissRequest()
                    navController.popBackStack()
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onError,
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        title = {
            Text(text = "정말로 삭제하시겠습니까?")
        },
        text = {
            if (viewModel.routine) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(
                        checked = viewModel.stopRoutine,
                        onCheckedChange = { viewModel.setStopRoutine(it) })
                    Text(text = "앞으로 반복하지 않기")
                }
            }
        }
    )
}

@Preview
@Composable
fun CheckListModifyScreenPreview() {
    NeulHaeRangTheme {
        CheckListModifyScreen(navController = rememberNavController(), null, 0)
    }
}
