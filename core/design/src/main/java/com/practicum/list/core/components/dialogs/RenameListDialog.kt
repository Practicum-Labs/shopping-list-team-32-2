package com.practicum.list.core.components.dialogs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.Dimens.DialogVerticalPadding
import com.practicum.list.core.theme.R
import com.practicum.list.core.theme.ShoppingListTheme

@Composable
fun RenameListDialog(
    modifier: Modifier = Modifier,
    textEditText: String = "",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
    confirmEnabled: Boolean = false
) {
    CustomLayoutDialog(
        modifier = modifier,
        onDismiss = onDismiss,
        titleAlignment = Alignment.Start,
        title = { RenameListDialogTitle() },
        content = {
            CreateListDialogContent(
                textEditText = textEditText,
                interactionSource = interactionSource,
                onKeyboardDone = onKeyboardDone,
                onTextChange = onTextChange
            )
        },
        confirmButton = {
            DialogButton(
                textRes = R.string.rename_list_dialog_rename_button_text,
                onClick = onConfirm,
                enabled = confirmEnabled
            )
        },
        dismissButton = { modifier ->
            DialogButton(
                modifier = modifier,
                textRes = R.string.cancel_dialog_general_text,
                onClick = onDismiss
            )
        }
    )
}

@Composable
private fun RenameListDialogTitle() {
    Text(
        modifier = Modifier
            .padding(top = DialogVerticalPadding, bottom = 8.dp),
        text = stringResource(R.string.rename_list_dialog_title_text),
        style = MaterialTheme.typography.bodyLarge
            .copy(color = MaterialTheme.colorScheme.surfaceBright)
    )
}

@Composable
@Preview(showSystemUi = true)
fun RenameListDialogPreviewLight() {
    ShoppingListTheme(false) {
        RenameListDialog()
    }
}

@Composable
@Preview(showSystemUi = true)
fun RenameListDialogPreviewDark() {
    ShoppingListTheme(true) {
        RenameListDialog()
    }
}