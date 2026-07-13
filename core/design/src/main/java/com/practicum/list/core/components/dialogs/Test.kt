package com.practicum.list.core.components.dialogs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.textedits.CustomTextEdit
import com.practicum.list.core.theme.Dimens.DialogHorizontalPadding
import com.practicum.list.core.theme.Dimens.DialogVerticalPadding
import com.practicum.list.core.theme.R
import com.practicum.list.core.theme.ShoppingListTheme

@Composable
fun CreateListDialog(
    modifier: Modifier = Modifier,
    textEditText: String = "",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
    confirmEnabled: Boolean = true
) {
    CustomLayoutDialog(
        modifier = modifier,
        onDismiss = onDismiss,
        iconRes = R.drawable.ic_docs_add_on,
        title = { CreateListDialogTitle() },
        content = {
            CreateListContent(
                textEditText = textEditText,
                interactionSource = interactionSource,
                onKeyboardDone = onKeyboardDone,
                onTextChange = onTextChange
            )
        },
        confirmButton = {
            DialogButton(
                textRes = R.string.new_list_dialog_create_button_text,
                onClick = onConfirm,
                enabled = confirmEnabled
            )
        },
        dismissButton = { modifier ->
            DialogButton(
                modifier = modifier,
                textRes = R.string.cancel_general_text,
                onClick = onDismiss
            )
        }
    )
}

@Composable
@Preview(showSystemUi = true)
fun CreateListDialogPreview() {
    ShoppingListTheme(false) {
        CreateListDialog()
    }
}

@Composable
private fun TextInputContent(
    textEditLabelRes: Int,
    textEditText: String,
    interactionSource: MutableInteractionSource,
    onKeyboardDone: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
    }
}

@Composable
fun CreateListContent(
    textEditText: String,
    interactionSource: MutableInteractionSource,
    onKeyboardDone: () -> Unit,
    onTextChange: (String) -> Unit
) {
    TextInputContent(
        textEditLabelRes = R.string.new_list_label_text,
        textEditText = textEditText,
        interactionSource = interactionSource,
        onKeyboardDone = { onKeyboardDone() },
        onTextChange = { onTextChange(it) }
    )
}

@Composable
fun DialogButton(
    textRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
        enabled = enabled
    ) {
        Text(
            color = MaterialTheme.colorScheme.secondary,
            text = stringResource(textRes),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun CreateListDialogTitle() {
    Text(
        modifier = Modifier.padding(vertical = 16.dp),
        text = stringResource(R.string.new_list_dialog_title_text),
        style = MaterialTheme.typography.headlineLarge
            .copy(color = MaterialTheme.colorScheme.surfaceBright)
    )
}
