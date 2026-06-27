package com.practicum.list.core.theme.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun CustomDialog(
    iconRes: Int,
    titleTextRes: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(painterResource(iconRes), contentDescription = null) },
        title = { Text(stringResource(titleTextRes)) },
        text = { Text("Alert message content.") },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("OK") }
        }
    )
}
