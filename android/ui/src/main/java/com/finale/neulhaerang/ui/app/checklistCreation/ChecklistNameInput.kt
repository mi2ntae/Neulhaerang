package com.finale.neulhaerang.ui.app.checklistCreation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finale.neulhaerang.common.Stat

@Composable
fun CheckListNameInput() {
    var value by remember { mutableStateOf("") }
    val (stat, setStat) = remember { mutableStateOf(Stat.GodSang) }
    var showDialog by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedIconButton(onClick = { showDialog = true }) {
            Icon(imageVector = Icons.Filled.QuestionMark, contentDescription = "icon")
        }
        Spacer(modifier = Modifier.width(8.dp))
        TextField(value = value,
            onValueChange = { value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "할 일을 입력해 주세요") },
            trailingIcon = {
                IconButton(onClick = { value = "" }) {
                    Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "clear")
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                errorContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        if (showDialog) {
            StatDialog(onDismiss = { showDialog = false }, stat, setStat)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatDialog(onDismiss: () -> Unit = {}, stat: Stat, setStat: (Stat) -> Unit) {
    AlertDialog(onDismissRequest = onDismiss, confirmButton = {}, title = {
        Text(text = "스탯을 골라주세요")
    }, text = {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)
        ) {
            Stat.values().forEach {
                item {
                    val colors = if (it == stat) ButtonDefaults.buttonColors()
                    else ButtonDefaults.outlinedButtonColors()
                    val border = if (it == stat) null
                    else ButtonDefaults.outlinedButtonBorder

                    Button(
                        onClick = { setStat(it); onDismiss() },
                        colors = colors,
                        border = border,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = it.statName)
                    }
                }
            }
        }
    })
}
