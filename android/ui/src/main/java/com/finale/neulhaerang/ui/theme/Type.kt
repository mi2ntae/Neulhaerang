package com.finale.neulhaerang.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.finale.neulhaerang.ui.R

val orbit = FontFamily(
    Font(R.font.orbit_regular, FontWeight.Normal, FontStyle.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = orbit,
    h1 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontSize = 57.sp,
        lineHeight = 64.sp
    ),
    h2 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    h3 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    h4 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    h5 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    h6 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    subtitle1 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    subtitle2 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    body1 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    body2 = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    button = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    caption = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    overline = TextStyle(
        color = Color(0xFF323232),
        fontFamily = orbit,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )
)