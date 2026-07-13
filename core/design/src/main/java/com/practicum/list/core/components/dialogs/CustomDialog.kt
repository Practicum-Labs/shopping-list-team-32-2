package com.practicum.list.core.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.practicum.list.core.theme.Dimens.DialogIconDimension
import com.practicum.list.core.theme.Dimens.DialogVerticalPadding

@Composable
fun CustomLayoutDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    iconRes: Int? = null,
    title: @Composable () -> Unit,
    content: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (modifier: Modifier) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(28.dp)
        ) {
            if (iconRes != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DialogIcon(
                        iconRes = iconRes,
                        modifier = Modifier.padding(top = DialogVerticalPadding)
                    )
                }
            }
            Column {
                title()
                content?.invoke()
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = DialogVerticalPadding)
            ) {
                dismissButton(Modifier.padding(start = 8.dp, end = 24.dp))
                confirmButton()
            }
        }
    }
}


@Composable
fun DialogIcon(
    iconRes: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(DialogIconDimension)
            .height(DialogIconDimension)
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}