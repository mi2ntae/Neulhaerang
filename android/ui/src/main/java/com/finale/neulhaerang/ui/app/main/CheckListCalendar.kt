package com.finale.neulhaerang.ui.app.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finale.neulhaerang.domain.MainScreenViewModel
import com.finale.neulhaerang.ui.R
import com.finale.neulhaerang.ui.app.fragment.NHLDatePicker
import com.finale.neulhaerang.ui.theme.Typography
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 *  CheckListCalendar
 *  루틴과 To do를 보여주는 캘린더
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
//    currentDate: LocalDate,
    selectedDate: LocalDate,
    setDateTime: (LocalDateTime) -> Unit,
) {
    var showSheet by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        selectedDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    )
    val lastDay = selectedDate.lengthOfMonth()
    val days = IntRange(1, lastDay).toList()
    Row(
        modifier = modifier.padding(start = 8.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { showSheet = true }) {
            Text(
                text = selectedDate.format(DateTimeFormatter.ofPattern("LLLL yyyy")),
                style = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.Bold)),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                tint = Color(0xFF343434),
                contentDescription = null,
                modifier = modifier.size(20.dp)
            )
        }
        NHLDatePicker(open = showSheet,
            close = { showSheet = false },
            datePickerState = datePickerState,
//            dateValidator = {
//                it >= LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
//            },
            onOk = {
                val inputDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis as Long)
                    .atZone(ZoneId.systemDefault()).toLocalDate()
                setDateTime(selectedDate.atStartOfDay().with(inputDate))
//                lastDay = selectedDate.atStartOfDay().with(inputDate).toLocalDate().lengthOfMonth()
//                days = IntRange(1, lastDay).toList()
            })
    }
    DaysRow(days = days, selectedDate = selectedDate, setDateTime = setDateTime)
}

@Composable
fun DaysRow(
    modifier: Modifier = Modifier,
    days: List<Int>,
    selectedDate: LocalDate,
    setDateTime: (LocalDateTime) -> Unit,
//    days: List<Day>
) {
    val viewModel = viewModel<MainScreenViewModel>(MainScreenViewModel.storeOwner)

    val index = if (selectedDate.dayOfMonth - 4 < 0) 0 else selectedDate.dayOfMonth - 4
    val listState = rememberLazyListState(index)
    LaunchedEffect(selectedDate) {
        listState.animateScrollToItem(index)
    }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.padding(bottom = 8.dp),
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
        state = listState
    ) {
//        items(days) { item -> DayElement(item.day, item.progress) }
        items(items = days, key = { it }) { item ->
            DayElement(
                daysOfWeek = selectedDate.withDayOfMonth(item).dayOfWeek,
                progressDegree = ((viewModel.todoDoneList.getOrNull(item - 1)?.ratio
                    ?: 0.0F) * 100).toInt(),
                day = item,
                selectedDate = selectedDate,
                setDateTime = setDateTime
            )
        }
    }
}

@Composable
fun DayElement(
    modifier: Modifier = Modifier,
    daysOfWeek: DayOfWeek,
    progressDegree: Int,
    day: Int,
    selectedDate: LocalDate,
    setDateTime: (LocalDateTime) -> Unit,
) {
    val isSelected = selectedDate.dayOfMonth == day
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color.Gray else Color.Transparent)
            .padding(horizontal = 4.dp),
    ) {
        Text(
            text = stringResource(
                changeDaysOfWeekToStringRes(
                    daysOfWeek.getDisplayName(
                        java.time.format.TextStyle.FULL, Locale.KOREAN
                    )
                )
            ), style = if (isSelected) Typography.bodyMedium.merge(
                TextStyle(
                    Color(0xFFFFFFFF), fontWeight = FontWeight.Bold
                )
            ) else Typography.bodyMedium
        )

        Box(
            modifier = modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF0F9D58), Color(0xF055CA4D)
                        )
                    )
                )
                .clickable {
                    setDateTime(
                        selectedDate
                            .withDayOfMonth(day)
                            .atStartOfDay()
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(weather(progressDegree)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
            )
        }
        Text(
            text = day.toString(), style = if (isSelected) Typography.bodyMedium.merge(
                TextStyle(
                    Color(0xFFFFFFFF), fontWeight = FontWeight.Bold
                )
            ) else Typography.bodyMedium
        )

    }
}


@StringRes
fun changeDaysOfWeekToStringRes(daysOfWeek: String): Int = when (daysOfWeek) {
    "월요일" -> R.string.monday
    "화요일" -> R.string.tuesday
    "수요일" -> R.string.wednesday
    "목요일" -> R.string.thursday
    "금요일" -> R.string.friday
    "토요일" -> R.string.saturday
    "일요일" -> R.string.sunday
    else -> R.string.monday
}

@DrawableRes
fun weather(progressDegree: Int) = when (progressDegree) {
    in 0..40 -> R.drawable.bad
    in 41..70 -> R.drawable.soso
    else -> R.drawable.good
}