package com.finale.neulhaerang.ui.watch.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.TitleCard
import com.finale.neulhaerang.common.navigation.WatchNavItem

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "MainScreen!",
            modifier = Modifier.fillMaxSize(1f),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Box(modifier = Modifier
            .clickable {
                navController.navigate(WatchNavItem.Stats.route)
            }
            .fillMaxSize(1f)
            .background(Color.Yellow)
        ) {

        }
    }

}