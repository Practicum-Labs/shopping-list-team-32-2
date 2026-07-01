package com.practicum.list.core.components.dialogs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.practicum.list.core.components.CustomTextEdit
import com.practicum.list.core.theme.Dimens.DialogHorizontalPadding
import com.practicum.list.core.theme.Dimens.DialogIconDimension
import com.practicum.list.core.theme.Dimens.DialogVerticalPadding

@Composable
fun CustomLayoutDialog(
    titleTextRes: Int,
    iconRes: Int,
    textEditLabelRes: Int,
    primaryButtonTextRes: Int,
    secondaryButtonTextRes: Int,
    textEditText: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onTextChange: (String) -> Unit,
    onKeyboardDone: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DialogIcon(
                    iconRes,
                    modifier = Modifier.padding(top = DialogVerticalPadding)
                )
                Text(
                    text = stringResource(titleTextRes),
                    style = MaterialTheme.typography.headlineLarge
                        .copy(color = MaterialTheme.colorScheme.surfaceBright),
                    modifier = Modifier.padding(
                        vertical = 16.dp,
                        horizontal = DialogVerticalPadding
                    )
                )
                CustomTextEdit(
                    modifier = Modifier
                        .padding(
                            vertical = DialogVerticalPadding,
                            horizontal = DialogHorizontalPadding
                        ),
                    labelTextRes = textEditLabelRes,
                    textString = textEditText,
                    interactionSource = interactionSource,
                    onKeyboardDone = { onKeyboardDone() },
                    onTextChange = { onTextChange(it) }
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(bottom = DialogVerticalPadding)
                ) {
                    DialogButton(primaryButtonTextRes) { onConfirm() }
                    DialogButton(
                        secondaryButtonTextRes,
                        Modifier.padding(start = 8.dp, end = 24.dp)
                    ) { onDismiss() }
                }
            }
        }
    }
}

@Composable
fun DialogButton(
    textRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(textRes),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )
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
