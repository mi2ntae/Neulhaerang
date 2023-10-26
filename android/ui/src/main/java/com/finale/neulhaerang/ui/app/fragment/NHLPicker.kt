package com.finale.neulhaerang.ui.app.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NHLDatePickerDialog(localDate: LocalDate = LocalDate.now()) {
    NeulHaeRangTheme {
        val datePickerState = rememberDatePickerState(
            localDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        )

        Column {
            DatePicker(state = datePickerState)
        }
    }
}
