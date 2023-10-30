package com.finale.neulhaerang.ui.app.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 *  ClosetPage
 *  의상실을 나타내는 UI 입니다.
 *  표정(1), 가방(2), 스킨(3), 안경(4), 모자(5), 무기(6), 목도리(7)를 선택할 수 있습니다.
 */

@Composable
fun ClosetPage() {
    val (screenState, setScreenState) = remember { mutableStateOf(1) }

    // 7개의 버튼을 만들고 클릭할 때마다 screenState 업데이트
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(onClick = { setScreenState(1) }) {
                Text(text = "표정", color = Color.White)
            }
            Button(onClick = { setScreenState(2) }) {
                Text(text = "가방", color = Color.White)
            }
            Button(onClick = { setScreenState(3) }) {
                Text(text = "스킨", color = Color.White)
            }
            Button(onClick = { setScreenState(4) }) {
                Text(text = "안경", color = Color.White)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(onClick = { setScreenState(5) }) {
                Text(text = "모자", color = Color.White)
            }
            Button(onClick = { setScreenState(6) }) {
                Text(text = "무기", color = Color.White)
            }
            Button(onClick = { setScreenState(7) }) {
                Text(text = "목도리", color = Color.White)
            }
        }

        // 현재 상태에 따라 화면 내용 변경
        when (screenState) {
            1 -> Screen1Content()
            2 -> Screen2Content()
            3 -> Screen3Content()
            4 -> Screen4Content()
            5 -> Screen5Content()
            6 -> Screen6Content()
            7 -> Screen7Content()
        }
    }
}

/**
 *  표정 요소가 들어있는 화면
 */

@Composable
fun Screen1Content() {
    ItemContent("표정")
}

/**
 *  가방 요소가 들어있는 화면
 */
@Composable
fun Screen2Content() {
    ItemContent("가방")
}

/**
 *  스킨 요소가 들어있는 화면
 */
@Composable
fun Screen3Content() {
    ItemContent("스킨")
}

/**
 *  안경 요소가 들어있는 화면
 */
@Composable
fun Screen4Content() {
    ItemContent("안경")
}

/**
 *  모자 요소가 들어있는 화면
 */
@Composable
fun Screen5Content() {
    ItemContent("모자")
}

/**
 *  무기 요소가 들어있는 화면
 */
@Composable
fun Screen6Content() {
    ItemContent("무기")
}

/**
 *  목도리 요소가 들어있는 화면
 */
@Composable
fun Screen7Content() {
    ItemContent("목도리")
}

@Composable
fun ItemContent(item_type : String){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Filled.Image, contentDescription = "임시 아이콘")
            }

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Filled.Image, contentDescription = "임시 아이콘")
            }

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Filled.Image, contentDescription = "임시 아이콘")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("$item_type 1")
            Text("$item_type 2")
            Text("$item_type 3")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Filled.Image, contentDescription = "임시 아이콘")
            }

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Filled.Image, contentDescription = "임시 아이콘")
            }

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Filled.Image, contentDescription = "임시 아이콘")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("$item_type 4")
            Text("$item_type 5")
            Text("$item_type 6")
        }

    }
}