package com.practicum.list.core.theme.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundIconButton(
    resId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    disabledContainerColor: Color? = null,
    disabledContentColor: Color? = null,
    enabled: Boolean = true,
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier
            .height(40.dp)
            .width(40.dp),
        shape = IconButtonDefaults.filledShape,
        enabled = enabled,
        colors = IconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor?:containerColor,
            disabledContentColor = disabledContentColor?:contentColor
        )
    ) {
        Icon(
            painter = painterResource(resId),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun PawIcon() = RoundIconButton(
    com.practicum.list.core.theme.R.drawable.ic_list_cart,
    {},
    contentColor = MaterialTheme.colorScheme.primaryContainer,
    containerColor = MaterialTheme.colorScheme.secondaryContainer,
    enabled = true
)
