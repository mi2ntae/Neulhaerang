package com.finale.neulhaerang.ui.app.checklist

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.domain.CheckListCreationViewModel
import com.finale.neulhaerang.ui.R
import com.finale.neulhaerang.ui.app.fragment.NHLTimePicker
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListCreationScreen(navController: NavHostController) {
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
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        )
    }) {
        CheckListCreationContent(
            modifier = Modifier
                .padding(paddingValues = it)
                .padding(all = 16.dp)
                .imePadding()
                .fillMaxSize(), navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListCreationContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val viewModel = viewModel<CheckListCreationViewModel>()

    val alarm = viewModel.alarm
    val dateTime = viewModel.dateTime
    val routine = viewModel.routine
    val timeHour = viewModel.timeHour
    val timeMinute = viewModel.timeMinute
    val changeAlarm = viewModel::changeAlarm
    val changeTime = viewModel::changeTime
    val changeRoutine = viewModel::changeRoutine
    val makeChecklist = viewModel::makeChecklist

    Column(modifier = modifier) {
        CheckListContentInput()
        Spacer(modifier = Modifier.height(8.dp))
        CheckListItem(
            name = stringResource(id = R.string.checklist_category_routine),
            icon = Icons.Filled.Refresh
        ) {
            Switch(checked = routine, onCheckedChange = changeRoutine)
        }
        if (routine) RoutineCreation() else TodoCreation()
        CheckListItem(
            name = stringResource(id = R.string.checklist_category_time),
            icon = Icons.Filled.Schedule
        ) {
            var showSheet by remember { mutableStateOf(false) }
            val timePickerState = rememberTimePickerState(
                initialHour = timeHour,
                initialMinute = timeMinute,
                is24Hour = false
            )

            TextButton(onClick = { showSheet = true }) {
                Text(text = dateTime.format(DateTimeFormatter.ofPattern("h:mm a")))
            }
            NHLTimePicker(open = showSheet,
                close = { showSheet = false },
                timePickerState = timePickerState,
                onOk = { changeTime(timePickerState.hour, timePickerState.minute) })
        }
        CheckListItem(
            name = stringResource(id = R.string.checklist_category_notice),
            icon = Icons.Filled.Alarm
        ) {
            Switch(checked = alarm, onCheckedChange = changeAlarm)
        }
        Spacer(modifier = Modifier.weight(weight = 1f))
        Button(
            onClick = {
                makeChecklist()
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

@Preview
@Composable
fun CheckListCreationScreenPreview() {
    NeulHaeRangTheme {
        CheckListCreationScreen(navController = rememberNavController())
    }
}
