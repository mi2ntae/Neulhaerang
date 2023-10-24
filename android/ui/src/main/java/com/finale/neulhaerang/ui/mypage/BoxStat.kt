package com.finale.neulhaerang.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * BoxStat
 * 능력치를 박스 형태로 나타내는 UI
 * App과 Watch에서 같이 사용함
 */

@Composable
fun BoxStat() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(text = "박스로 된 능력치입니당")
    }
}
