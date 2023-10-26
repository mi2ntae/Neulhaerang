import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.finale.neulhaerang.ui.R

/**
 * BoxStat
 * 능력치를 박스 형태로 나타내는 UI
 * 문자열 형태의 등급을 parameter로 받음.
 *
 * parameter : 갓생력, 생존력, 인싸력, 튼튼력, 창의력, 최애력
 *
 */

@Composable
fun BoxStat(
    godSangStat: String,
    surviveStat: String,
    inSsaStat: String,
    teunteunStat: String,
    goodIdeaStat: String,
    LoveStat: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            /**
             *  R.string.ui_XXXXX
             *  해당 항목에 속하는 요소는 아래 경로 참고
             *  android\ui\src\main\res\values\strings.xml
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                StatButton(stringResource(id = R.string.ui_god_sang), godSangStat)
                StatButton(stringResource(id = R.string.ui_survive), surviveStat)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                StatButton(stringResource(id = R.string.ui_in_ssa), inSsaStat)
                StatButton(stringResource(id = R.string.ui_teunteun), teunteunStat)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                StatButton(stringResource(id = R.string.ui_good_idea), goodIdeaStat)
                StatButton(stringResource(id = R.string.ui_love), LoveStat)
            }
        }
    }
}

/**
 *  StatButton
 *  능력치를 나타내는 box 형태의 button
 *  클릭했을때, 변동량을 보여주기 위하여 button으로 설계함.
 */
@Composable
fun StatButton(name : String, grade : String){
    ExtendedFloatingActionButton(onClick = {
        /* TODO */
        // 버튼을 눌렀을 때, 창을 띄워 변화량과 그래프를 볼 수 있게 해야함.
    }) {
        /* TODO */
        // 아이콘 dependency 추가되면, 각각의 능력치 별로 알맞는 아이콘 추가해야함
        Text(text = "$name $grade")
    }
}