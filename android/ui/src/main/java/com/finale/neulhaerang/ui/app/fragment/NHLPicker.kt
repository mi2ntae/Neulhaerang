@file:OptIn(ExperimentalMaterial3Api::class)

package com.finale.neulhaerang.ui.app.fragment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NHLDatePicker(
    showSheet: MutableState<Boolean>,
    datePickerState: DatePickerState,
    dateValidator: (Long) -> Boolean = { true },
    onOk: () -> Unit
) {
    PickerModalBottomSheet(showSheet = showSheet) {
        DatePicker(state = datePickerState, dateValidator = dateValidator, title = {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onOk() }) { Text(text = "완료") }
            }
        })
    }
}

@Composable
fun PickerModalBottomSheet(
    showSheet: MutableState<Boolean>, content: @Composable () -> Unit
) {
    if (showSheet.component1()) {
        ModalBottomSheet(
            onDismissRequest = { showSheet.component2()(false) },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            content()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
