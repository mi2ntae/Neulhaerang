package com.finale.neulhaerang.ui.app.checklistCreation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finale.neulhaerang.domain.ChecklistCreationViewModel
import com.finale.neulhaerang.ui.R
import com.finale.neulhaerang.ui.app.fragment.NHLDatePicker
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@Composable
fun ChecklistCreationItem(
    modifier: Modifier = Modifier,
    name: String,
    icon: ImageVector,
    content: @Composable (() -> Unit)?,
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
        content?.let { it() }
    }
}

@Composable
fun RoutineCreation(
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<ChecklistCreationViewModel>()

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
                val colors = if (viewModel.repeat.value[i]) ButtonDefaults.buttonColors()
                else ButtonDefaults.outlinedButtonColors()
                val border = if (viewModel.repeat.value[i]) null
                else ButtonDefaults.outlinedButtonBorder

                Button(
                    onClick = { viewModel.changeRepeat(i) },
                    modifier = Modifier.size(48.dp),
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
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<ChecklistCreationViewModel>()

    var showSheet by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(viewModel.dateMilli)

    ChecklistCreationItem(
        modifier = modifier,
        name = stringResource(id = R.string.checklist_category_date),
        icon = Icons.Filled.DateRange
    ) {
        TextButton(onClick = { showSheet = true }) {
            Text(text = viewModel.dateTime.value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        }
        // 날짜 피커 모달 바텀 시트
        NHLDatePicker(open = showSheet,
            close = { showSheet = false },
            datePickerState = datePickerState,
            dateValidator = {
                it >= LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
            },
            onOk = { datePickerState.selectedDateMillis?.let { viewModel.changeDate(it) } })
    }
}
