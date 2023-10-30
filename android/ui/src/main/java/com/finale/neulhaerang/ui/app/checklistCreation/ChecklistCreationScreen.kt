package com.finale.neulhaerang.ui.app.checklistCreation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.finale.neulhaerang.ui.R
import com.finale.neulhaerang.ui.app.fragment.NHLTimePicker
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistCreationScreen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                text = "체크리스트 작성"
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back)
                )
            }
        }, actions = {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.complete),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(modifier: Modifier = Modifier) {
    // 동작 확인용 변수
    // TODO: ViewModel 구현
    val (routine, setRoutine) = remember { mutableStateOf(true) }
    val (dateTime, setDateTime) = remember { mutableStateOf(LocalDateTime.now()) }
    val (alram, setAlram) = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row {
            Text(text = stringResource(id = R.string.checklist_category_name))
        }
        ChecklistCreationItem(
            name = stringResource(id = R.string.checklist_category_routine),
            icon = Icons.Filled.Refresh
        ) {
            Switch(checked = routine, onCheckedChange = setRoutine)
        }
        if (routine) RoutineCreation() else TodoCreation(
            dateTime = dateTime, setDateTime = setDateTime
        )
        ChecklistCreationItem(
            name = stringResource(id = R.string.checklist_category_time),
            icon = Icons.Filled.Schedule
        ) {
            var showSheet by remember { mutableStateOf(false) }
            val timePickerState = rememberTimePickerState(
                initialHour = dateTime.hour, initialMinute = dateTime.minute, is24Hour = false
            )

            TextButton(onClick = { showSheet = true }) {
                Text(text = dateTime.format(DateTimeFormatter.ofPattern("h:mm a")))
            }
            NHLTimePicker(
                open = showSheet,
                close = { showSheet = false },
                timePickerState = timePickerState,
                onOk = {
                    setDateTime(
                        dateTime.withHour(timePickerState.hour).withMinute(timePickerState.minute)
                    )
                })
        }
        ChecklistCreationItem(
            name = stringResource(id = R.string.checklist_category_notice),
            icon = Icons.Filled.Alarm
        ) {
            Switch(checked = alram, onCheckedChange = setAlram)
        }
    }
}

@Preview
@Composable
fun ChecklistCreationScreenPreview() {
    NeulHaeRangTheme {
//        ChecklistCreationScreen(navController = rememberNavController())
        RoutineCreation()
    }
}
