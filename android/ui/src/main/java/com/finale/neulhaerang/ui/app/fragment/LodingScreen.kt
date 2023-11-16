package com.finale.neulhaerang.ui.app.fragment

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingScreen() {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }
}

@Composable
fun LoadingBar() {
//    Column(modifier = Modifier.padding(vertical = 16.dp)) {
    LinearProgressIndicator(modifier = Modifier
        .padding(vertical = 16.dp, horizontal = 8.dp)
        .fillMaxWidth())
//    }
}