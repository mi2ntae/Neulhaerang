package com.finale.neulhaerang.ui.watch

import CheckListScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.finale.neulhaerang.common.navigation.WatchNavItem
import com.finale.neulhaerang.ui.watch.main.MainScreen
import com.finale.neulhaerang.ui.watch.stats.ViewStatsScreen


@Composable
fun WatchMain() {
//    NeulHaeRangTheme {
//        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
//         * version of LazyColumn for wear devices with some added features. For more information,
//         * see d.android.com/wear/compose.
//         */
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Greeting(greetingName = greetingName)
//        }
//    }
    val navController = rememberSwipeDismissableNavController()
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = WatchNavItem.Main.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(WatchNavItem.Main.route) {
            MainScreen(navController = navController)
        }
        composable(WatchNavItem.Stats.route) {
            ViewStatsScreen()
        }
        composable(WatchNavItem.Checklist.route) {
            CheckListScreen()
        }

    }
}


@Composable
fun Greeting(greetingName: String) {
    Text(
        text = "From the Hello world,\nHello, $greetingName!",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WatchMain()
}