@file:OptIn(ExperimentalMaterial3Api::class)

package com.finale.neulhaerang.ui.app.fragment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.finale.neulhaerang.ui.R
import kotlinx.coroutines.launch

@Composable
fun NHLDatePicker(
    open: Boolean,
    close: () -> Unit,
    datePickerState: DatePickerState,
    dateValidator: (Long) -> Boolean = { true },
    onOk: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (open) {
        ModalBottomSheet(
            onDismissRequest = { close() }, sheetState = sheetState, dragHandle = null
        ) {
            DatePicker(state = datePickerState, dateValidator = dateValidator, title = {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onOk()
                        scope.launch { sheetState.hide() }.invokeOnCompletion { close() }
                    }) { Text(text = stringResource(id = R.string.complete)) }
                }
            }, showModeToggle = false)
        }
    }
}

@Composable
fun NHLTimePicker(
    open: Boolean, close: () -> Unit, timePickerState: TimePickerState, onOk: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (open) {
        ModalBottomSheet(
            onDismissRequest = { close() }, sheetState = sheetState, dragHandle = null
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onOk()
                        scope.launch { sheetState.hide() }.invokeOnCompletion { close() }
                    }) { Text(text = stringResource(id = R.string.complete)) }
                }
                TimeInput(state = timePickerState)
            }
        }
    }
}
