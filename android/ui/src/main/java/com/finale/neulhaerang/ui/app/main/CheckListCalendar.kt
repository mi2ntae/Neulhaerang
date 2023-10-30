package com.finale.neulhaerang.ui.app.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finale.neulhaerang.data.Day
import com.finale.neulhaerang.ui.R

/**
 *  CheckListCalendar
 *  루틴과 To do를 보여주는 캘린더
 */
@Composable
fun Calendar() {
    val days = listOf<Day>(
        Day("월요일", 80),
        Day("화요일", 100),
        Day("수요일", 30),
        Day("목요일", 50),
        Day("화요일", 100),
        Day("화요일", 100),
        Day("수요일", 30),
        Day("목요일", 50),
        Day("화요일", 100),
        Day("화요일", 100),
        Day("수요일", 30),
        Day("목요일", 50),
        Day("화요일", 100)
    )
    DaysRow(days = days)
}

@Composable
fun DayElement(
    daysOfWeek: String,
    progressDegree: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(text = stringResource(changeDaysOfWeekToStringRes(daysOfWeek)))
        Box(
            modifier = modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(weather(progressDegree)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Blue)
            )
        }
        Text(text = "15")
    }
}

@Composable
fun DaysRow(
    modifier: Modifier = Modifier,
    days: List<Day>
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(days) { item -> DayElement(item.day, item.progress) }
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