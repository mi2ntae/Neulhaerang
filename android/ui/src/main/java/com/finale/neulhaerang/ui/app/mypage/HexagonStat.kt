package com.finale.neulhaerang.ui.app.mypage

import android.view.View
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding

import com.finale.neulhaerang.ui.databinding.FragmentBinding


/**
 *  HexagonStat
 *  육각 스탯 UI 입니다.
 *  6가지의 능력치를 받아 육각 그래프 형태로 표시합니다.
 */

@Composable
fun HexagonStat() {

    Text("육각 스텟 UI 입니당")
}

@Composable
fun CustomView() {
    var selectedItem by remember { mutableStateOf(0) }

    // Adds view to Compose
    AndroidView(
        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates view
            MyView(context).apply {
                // Sets up listeners for View -> Compose communication
                setOnClickListener {
                    selectedItem = 1
                }
            }
        },
        update = { view ->
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary

            // As selectedItem is read here, AndroidView will recompose
            // whenever the state changes
            // Example of Compose -> View communication
            view.selectedItem = selectedItem
        }
    )
}

@Composable
fun ContentExample() {
    Column(Modifier.fillMaxSize()) {
        Text("Look at this CustomView!")
        CustomView()
        AndroidViewBindingExample()
    }
}

class MyView(context: Context) : View(context) {
    var selectedItem: Int = 0
}

/*
진행중
https://github.com/android/snippets/blob/ac2103bc5dabcd49ad108a82ebaee8ed76e10a7f/compose/snippets/src/main/java/com/example/compose/snippets/interop/InteroperabilityAPIsSnippets.kt

 */

@Composable
fun AndroidViewBindingExample() {
    AndroidViewBinding(FragmentBinding::inflate) {
        exampleView.setBackgroundColor(Color.Gray)
    }
}