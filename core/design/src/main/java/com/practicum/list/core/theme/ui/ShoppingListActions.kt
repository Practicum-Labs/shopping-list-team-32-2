package com.practicum.list.core.theme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListActions(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onCopyClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .height(48.dp)
            .width(144.dp)
    ) {
        IconBox(
           resId = com.practicum.list.core.theme.R.drawable.ic_pencil,
            enabled = true,
            onClick = onEditClick
        )
        IconBox(
            resId = com.practicum.list.core.theme.R.drawable.ic_copy,
            enabled = true,
            onClick = onCopyClick
        )
        IconBox(
            resId = com.practicum.list.core.theme.R.drawable.ic_delete,
            enabled = true,
            onClick = onDeleteClick
        )
    }
}

@Composable
fun IconBox(
    resId: Int,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,

    ) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .width(48.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        RoundIconButton(
            resId,
            onClick,
            modifier.align(Alignment.Center),
            MaterialTheme.colorScheme.surfaceContainerHighest,
            MaterialTheme.colorScheme.onTertiaryContainer,
            enabled = enabled
        )
    }
}