package com.finale.neulhaerang.ui.app.checklistCreation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DatePicker
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistCreationScreen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = "체크리스트 작성") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
            }
        }, actions = {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "취소", color = MaterialTheme.colorScheme.secondary)
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "완료", color = MaterialTheme.colorScheme.primary)
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

@Composable
fun Content(modifier: Modifier = Modifier) {
    // 동작 확인용 변수
    // TODO: ViewModel 구현
    val (routine, setRoutine) = remember { mutableStateOf(false) }
    val (selectedDate, setSelectedDate) = remember { mutableStateOf(LocalDate.now()) }

    Column(modifier = modifier) {
        Row {
            Text(text = "체크리스트 이름")
        }
        ChecklistCreationItem(name = "루틴", icon = Icons.Filled.Refresh) {
            Switch(checked = routine, onCheckedChange = setRoutine)
        }
        if (routine) RoutineCreation() else TodoCreation(
            selectedDate = selectedDate, setSelectedDate = setSelectedDate
        )
        Row {
            Text(text = "시간")
        }
        Row {
            Text(text = "알림")
        }
    }
}

@Composable
fun ChecklistCreationItem(
    modifier: Modifier = Modifier, name: String, icon: ImageVector, content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = name)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = name)
        }
        content()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCreation(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    setSelectedDate: (LocalDate) -> Unit
) {
    ChecklistCreationItem(modifier = modifier, name = "날짜", icon = Icons.Filled.DateRange) {
        TextButton(onClick = { }) {
            Text(text = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        }
    }
}

@Preview
@Composable
fun ChecklistCreationScreenPreview() {
    NeulHaeRangTheme {
        ChecklistCreationScreen(navController = rememberNavController())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NHLDatePickerDialog() {
    NeulHaeRangTheme {
        val zoneId = ZoneId.systemDefault()
        val text = "${zoneId.toString()}\n" +
                "${
                    LocalDate.now(ZoneOffset.UTC).atStartOfDay(ZoneId.systemDefault()).toInstant()
                        .toEpochMilli()
                }\n" + "${
            LocalDate.now(ZoneOffset.UTC).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        }\n" +
                "${LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()}"

        val localDate = LocalDate.now()
        val datePickerState = rememberDatePickerState(
//            localDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
            localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        Column {
            DatePicker(state = datePickerState)
            Text(text = text)
        }
    }
}
