package com.finale.neulhaerang.ui.app.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Letter(
    modifier: Modifier = Modifier
        .padding(horizontal = 24.dp, vertical = 16.dp)
        .verticalScroll(rememberScrollState(0))
) {
    Box(modifier = modifier) {
        Text(
            text = "안녕, 싸피야!\n" + "\n" + "오늘은 내가 한 일들을 알려줄게. 아침에는 8시에 일어났어. 새로운 하루가 시작되는 건 항상 기대돼.\n" + "\n" + "오전에는 SSAFY 수업을 들었어. 오늘은 좀 어려운 프로그래밍 과제를 풀어봤어. 물론 동료들과 함께 협력하면서 문제를 해결했지. \uD83D\uDCAA\n" + "\n" + "점심 때는 친구들을 만나서 곱창전골을 먹었어. 맛있게 먹고 좋은 대화를 나누는 건 항상 즐거운 시간이야.\n" + "\n" + "양치하고 머리 감기도 잊지 않았어. 건강하게 지내려고 노력하고 있어. \uD83E\uDDB7 \uD83D\uDC87\n" + "\n" + "마지막으로, 1시에 잠자리에 들었어. 하루 동안 열심히 노력했지만, 늦게 자는 건 힘들어. \uD83D\uDE34\n" + "\n" + "앞으로도 같이 좋은 순간을 만들고 함께 성장해보자. 싸피야, 내일은 더 좋은 날이 오기를 기대해! \uD83D\uDE0A\uD83C\uDF1F"
        )
    }
}