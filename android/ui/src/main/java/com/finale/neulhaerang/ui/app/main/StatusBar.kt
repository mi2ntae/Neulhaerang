package com.finale.neulhaerang.ui.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finale.neulhaerang.ui.R
import com.finale.neulhaerang.ui.theme.Typography

/**
 *  StatusBar
 *  피로도, 나태함 상태바를 보여주는 UI
 */
@Composable
fun StatusBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)) {
        Row {
            Text(text = stringResource(id = R.string.main_tired))
            CustomProgressBar()
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = stringResource(id = R.string.main_lazy))
            CustomProgressBar()
        }
    }

}

// on below line we are creating a function for custom progress bar.
@Composable
fun CustomProgressBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val progress = 50
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .height(30.dp)
                .background(Color.Gray)
                .fillMaxWidth()
//                .padding(start = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .height(30.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF0F9D58), Color(0xF055CA4D)
                            )
                        )
                    )
                    // on below line we are specifying width for the inner box
//                    .width(300.dp * progress / 100)
                    .fillMaxWidth(1f * progress / 100)
            )
            // on below line we are creating a text for our box
            Text(
                text = "$progress %",
                modifier = Modifier.align(Alignment.Center),
                style = Typography.labelLarge,
//                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}