package com.finale.neulhaerang.ui.app.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finale.neulhaerang.ui.app.fragment.LoadingBar

@Composable
fun Letter(
    modifier: Modifier = Modifier,
    loading: Boolean,
    letterText: String,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState(0))
    ) {
        Spacer(modifier = Modifier.height(16.dp)) //편지 위아래 여백
        if (loading) {
            LoadingBar()
        } else if (letterText.isEmpty()) {
            Text(
                text = "아직 편지가 오지 않았어요\n" + "편지는 매일 밤 12시에 도착해요"
            )
        } else {
            Text(text = letterText)
        }
        Spacer(modifier = Modifier.height(56.dp))
    }
}