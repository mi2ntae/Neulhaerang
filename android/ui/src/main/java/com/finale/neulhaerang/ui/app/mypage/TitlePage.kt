package com.finale.neulhaerang.ui.app.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 *  TitleButton
 *  칭호를 나타내는 box 형태의 button
 *  클릭했을때, 착용 여부를 물어보는 팝업 나와야함.
 *
 *  icon : 칭호를 대표하는 아이콘
 *  name : 칭호 이름
 *
 */
@Composable
fun TitleButton(icon: String, name: String) {
    ExtendedFloatingActionButton(onClick = {
        /* TODO */
        // 버튼을 눌렀을 때, 착용 여부를 물어보는 팝업 띄워야함
    }) {
        /* TODO */
        // 아이콘 dependency 추가되면, 각각의 칭호별로 알맞는 아이콘 추가해야함
        Icon(
            imageVector = Icons.Filled.Accessible,
            contentDescription = "$icon",
        )
        Text(text = "$name")
    }
}

/**
 *  TitlePage
 *  칭호를 볼 수 있는 페이지입니다.
 */

@Composable
fun TitlePage() {
    val tempStringList = listOf(
        "시작이 반이다",
        "내 몸은 내가 챙긴다",
        "오늘도 파티",
        "건강이 최고",
        "해랑 사랑꾼",
        "매일매일이 가장 쉬웠어요",
        "피로가 극에 달한 자",
        "먹고는 살아야지",
        "해랑이 필기구 파괴자"
    )

    Text("추후 칭호는 이미지로 바꾸고, 인덱스로 접근할 예정입니다.")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            /**
             *  R.string.ui_XXXXX
             *  해당 항목에 속하는 요소는 아래 경로 참고
             *  android\ui\src\main\res\values\strings.xml
             */

            items(tempStringList.size){index ->
                TitleButton("알맞는 아이콘", tempStringList[index])
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

