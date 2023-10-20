package com.finale.neulhaerang


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finale.neulhaerang.navigation.AppBottomNavigationItem
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeulHaeRangTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    Scaffold(
        content = {
            NavHost(
                navController = navController,
                startDestination = AppBottomNavigationItem.values()[0].route,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                AppBottomNavigationItem.values()
                    .forEach { item ->
                        composable(route = item.route) {
                            item.screen()
                        }
                    }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                AppBottomNavigationItem.values().forEach {
                    val isSelected = it.route == backStackEntry.value?.destination?.route
                    NavigationBarItem(selected = isSelected,
                        onClick = { navController.navigate(it.route) },
                        icon = { Icon(imageVector = it.icon, contentDescription = it.title) },
                        label = {
                            Text(text = it.name, fontWeight = FontWeight.SemiBold)
                        }
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun Preview() {
    App()
}


