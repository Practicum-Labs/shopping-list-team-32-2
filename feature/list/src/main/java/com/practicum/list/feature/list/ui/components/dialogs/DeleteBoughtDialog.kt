package com.practicum.list.feature.list.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.practicum.list.core.components.dialogs.DeleteListDialog
import com.practicum.list.feature.list.R

@Composable
fun DeleteBoughtDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    DeleteListDialog(
        modifier = modifier,
        title = R.string.remove_all_bought_goods,
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}