package com.finale.neulhaerang.ui.app.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finale.neulhaerang.ui.theme.Pink40
import com.finale.neulhaerang.ui.theme.Pink80

/**
 * MyPageHeader
 * 마이페이지 상단 화면을 나타내는 UI
 * 캐릭터, 칭호, 코인, 인스타자랑, 레벨 포함
 */

@Composable
fun MyPageHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text("마이페이지 헤더입니당")
        CharacterInfo()
        Coin()
        Title()
        Share()
        Level(2, 4, 16)
    }
}

/**
 * EarnedTitle
 * 칭호를 표시하는 UI
 */

@Composable
fun Title() {
    Text("칭호입니당")
}

/**
 * Coin
 * 코인을 표시하는 UI
 */

@Composable
fun Coin() {
    Text("코인입니당")
}

/**
 * Share
 * SNS 자랑하기를 표시하는 UI
 */

@Composable
fun Share() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    /*TODO*/
                    // 공유 버튼 눌렀을 때, 인스타 공유하기 구현해야함
                }) {
                Icon(Icons.Filled.Share, contentDescription = "인스타 자랑하기")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text("자랑하기")
        }

    }
}

/**
 * Level
 * 레벨과 경험치를 표시하는 UI
 * Input
 *  - level : 현재 레벨
 *  - curEx : 현재 경험치
 *  - upEx : 레벨업 하기 위한 경험치
 */

@Composable
fun Level(level: Int, curEx: Int, upEx: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Lv.$level")

        // 간격 설정
        Spacer(modifier = Modifier.width(16.dp))

        // 경험치를 보여주는 막대
        LinearProgressIndicator(
            modifier = Modifier
                // 경험치 막대의 높이 설정
                .height(8.dp)
                .align(alignment = CenterVertically),

            progress = curEx / upEx.toFloat(),
            color = Pink40,
            trackColor = Pink80,
        )

        // 간격 설정
        Spacer(modifier = Modifier.width(16.dp))
        Text("$curEx / $upEx")
    }
}