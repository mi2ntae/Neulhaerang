package com.finale.neulhaerang.ui.app.checklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.finale.neulhaerang.domain.CheckListModifyViewModel
import com.finale.neulhaerang.ui.R
import kotlinx.coroutines.launch

/**
 * 체크리스트 관련 값확인 및 통신 에러 알림용 AlertDialog
 */
@Composable
fun CheckListAlertDialog(onDismissRequest: () -> Unit, message: String) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        title = {},
        text = { Text(text = message, style = MaterialTheme.typography.bodyLarge) }
    )
}

/**
 * 체크리스트 삭제 전 확인용 Dialog
 */
@Composable
fun CheckListDeleteDialog(
    navController: NavHostController,
    onDismissRequest: () -> Unit,
    setAlert: (Boolean) -> Unit,
    setMessage: (String) -> Unit,
    viewModel: CheckListModifyViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    scope.launch {
                        val message = viewModel.deleteCheckList() ?: ""
                        setMessage(message)
                        if (message.isBlank()) {
                            onDismissRequest()
                            navController.popBackStack()
                        } else {
                            setAlert(true)
                        }
                    }
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onError,
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        title = {
            Text(text = "정말로 삭제하시겠습니까?", style = MaterialTheme.typography.bodyLarge)
        },
        text = {
            if (viewModel.routine) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(
                        checked = viewModel.stopRoutine,
                        onCheckedChange = { viewModel.setStopRoutine(it) })
                    Text(text = "앞으로 반복하지 않기",
                        modifier = Modifier.clickable { viewModel.setStopRoutine(!viewModel.stopRoutine) })
                }
            }
        }
    )
}
