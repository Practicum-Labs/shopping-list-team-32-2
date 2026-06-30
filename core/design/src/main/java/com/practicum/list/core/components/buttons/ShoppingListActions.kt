package com.practicum.list.core.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.R

@Composable
fun ShoppingListActions(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onCopyClick: () -> Unit,
    onEditClick: () -> Unit,
) {

    val containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
    val contentColor = MaterialTheme.colorScheme.tertiary
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .height(48.dp)
            .width(144.dp)
    ) {
        IconBox(
           resId = R.drawable.ic_pencil,
            enabled = true,
            containerColor,
            contentColor,
            onClick = onEditClick
        )
        IconBox(
            resId = R.drawable.ic_copy,
            enabled = true,
            containerColor,
            contentColor,
            onClick = onCopyClick
        )
        IconBox(
            resId = R.drawable.ic_delete,
            enabled = true,
            containerColor,
            contentColor,
            onClick = onDeleteClick
        )
    }
}

@Composable
fun IconBox(
    resId: Int,
    enabled: Boolean,
    containerColor: Color,
    contentColor: Color,
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
            containerColor,
            contentColor,
            enabled = enabled
        )
    }
}