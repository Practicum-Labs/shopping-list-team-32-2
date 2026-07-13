package com.practicum.list.core.components.dialogs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.practicum.list.core.components.textedits.CustomTextEdit
import com.practicum.list.core.theme.Dimens.DialogHorizontalPadding
import com.practicum.list.core.theme.Dimens.DialogVerticalPadding

@Composable
fun TextInputDialog(
    titleTextRes: Int,
    iconRes: Int? = null,
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
    confirmEnabled: Boolean = true
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (iconRes != null) {
                    DialogIcon(
                        iconRes,
                        modifier = Modifier.padding(top = DialogVerticalPadding)
                    )
                }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = DialogVerticalPadding)
                ) {
                    DialogButton(
                        textRes = primaryButtonTextRes,
                        onClick = { onConfirm() },
                        enabled = confirmEnabled
                    )
                    DialogButton(
                        modifier = Modifier.padding(start = 8.dp, end = 24.dp),
                        textRes = secondaryButtonTextRes,
                        onClick = { onDismiss() }
                    )
                }
            }
        }
    }
}
