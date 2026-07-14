package com.practicum.list.feature.list.ui.components

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
import com.practicum.list.core.components.buttons.RoundIconButton
import com.practicum.list.core.theme.R

@Composable
fun ProductListActions(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerColor = MaterialTheme.colorScheme.secondaryContainer
    val contentColor = MaterialTheme.colorScheme.onSecondaryContainer

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .height(48.dp)
            .width(96.dp),
    ) {
        ProductActionIcon(
            resId = R.drawable.ic_pencil,
            containerColor = containerColor,
            contentColor = contentColor,
            onClick = onEditClick,
        )
        ProductActionIcon(
            resId = R.drawable.ic_delete,
            containerColor = containerColor,
            contentColor = contentColor,
            onClick = onDeleteClick,
        )
    }
}

@Composable
private fun ProductActionIcon(
    resId: Int,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .width(48.dp)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        RoundIconButton(
            enabled = true,
            resId = resId,
            onClick = onClick,
            modifier = Modifier.align(Alignment.Center),
            containerColor = containerColor,
            contentColor = contentColor,
        )
    }
}
