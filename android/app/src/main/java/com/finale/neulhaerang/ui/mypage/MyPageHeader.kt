package com.finale.neulhaerang.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * MyPageHeader
 * 마이페이지 상단 화면을 나타내는 UI
 * 캐릭터, 칭호, 코인, 인스타자랑, 레벨 포함
 */

@Composable
fun MyPageHeader() {
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.primary)){
        Text("마이페이지 헤더입니당")
        CharacterInfo()
        Coin()
        Title()
        Insta()
        Level()
    }
}

/**
 * EarnedTitle
 * 칭호를 표시하는 UI
 */

@Composable
fun Title(){
    Text("칭호입니당")
}

/**
 * Coin
 * 코인을 표시하는 UI
 */

@Composable
fun Coin(){
    Text("코인입니당")
}

/**
 * Insta
 * 인스타그램 자랑하기를 표시하는 UI
 */

@Composable
fun Insta(){
    Text("인스타 자랑하기")
}

/**
 * Level
 * 레벨과 경험치를 표시하는 UI
 */

@Composable
fun Level(){
    Text("레벨과 경험치")
}