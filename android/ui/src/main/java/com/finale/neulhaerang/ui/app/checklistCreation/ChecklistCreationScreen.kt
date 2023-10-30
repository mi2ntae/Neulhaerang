package com.finale.neulhaerang.ui.app.checklistCreation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.finale.neulhaerang.ui.R
import com.finale.neulhaerang.ui.app.fragment.NHLDatePicker
import com.finale.neulhaerang.ui.app.fragment.NHLTimePicker
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
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

@Composable
fun ChecklistCreationItem(
    modifier: Modifier = Modifier,
    name: String,
    icon: ImageVector,
    content: @Composable() (() -> Unit)?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = name)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = name)
        }
        if (content != null) {
            content()
        }
    }
}

@Composable
fun RoutineCreation(modifier: Modifier = Modifier) {
    val selectedDays = rememberSaveable { mutableStateOf(List<Boolean>(7) { _ -> false }) }

    Column(
        modifier = modifier
    ) {
        ChecklistCreationItem(
            name = stringResource(id = R.string.checklist_category_repeat),
            icon = Icons.Filled.Alarm,
            content = null
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0..6) {
                val colors =
                    if (selectedDays.value[i]) ButtonDefaults.buttonColors()
                    else ButtonDefaults.outlinedButtonColors()
                val border =
                    if (selectedDays.value[i]) null
                    else ButtonDefaults.outlinedButtonBorder

                Button(
                    onClick = {  },
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    colors = colors,
                    border = border,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = stringArrayResource(id = R.array.day_of_week_array_short_kr)[i])
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCreation(
    modifier: Modifier = Modifier, dateTime: LocalDateTime, setDateTime: (LocalDateTime) -> Unit
) {
    var showSheet by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
    )

    ChecklistCreationItem(
        modifier = modifier,
        name = stringResource(id = R.string.checklist_category_date),
        icon = Icons.Filled.DateRange
    ) {
        TextButton(onClick = { showSheet = true }) {
            Text(text = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        }
        // 날짜 피커 모달 바텀 시트
        NHLDatePicker(open = showSheet,
            close = { showSheet = false },
            datePickerState = datePickerState,
            dateValidator = {
                it >= LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
            },
            onOk = {
                val inputDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis as Long)
                    .atZone(ZoneId.systemDefault()).toLocalDate()
                setDateTime(dateTime.with(inputDate))
            })
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
