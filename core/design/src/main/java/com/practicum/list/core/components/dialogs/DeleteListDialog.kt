package com.practicum.list.core.components.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.R
import com.practicum.list.core.theme.ShoppingListTheme

private const val TEST_LIST_NAME = "Продукты"
@Composable
fun DeleteListDialog(
    modifier: Modifier = Modifier,
    listName: String,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    CustomLayoutDialog(
        modifier = modifier,
        onDismiss = onDismiss,
        iconRes = R.drawable.ic_delete_warning,
        title = { DeleteListDialogTitle(listName) },
        confirmButton = {
            DeleteButtonConfirmDialog(
                modifier = modifier,
                onClick = onConfirm
            )
        },
        dismissButton = { modifier ->
            DeleteButtonCancelDialog(
                modifier = modifier,
                onClick = onDismiss
            )
        }
    )
}

@Composable
fun DeleteListDialogTitle(listName: String) {
    Text(
        modifier = Modifier.padding(vertical = 16.dp),
        textAlign = TextAlign.Center,
        text = stringResource(R.string.delete_list_dialog_title_text, listName),
        style = MaterialTheme.typography.headlineLarge
            .copy(color = MaterialTheme.colorScheme.surfaceBright)
    )
}

@Composable
fun DeleteButtonConfirmDialog(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DeleteButtonDialog(
        modifier = modifier,
        textRes = R.string.delete_list_dialog_delete_button_text,
        buttonColor = MaterialTheme.colorScheme.error,
        textColor = MaterialTheme.colorScheme.onPrimary,
        onClick = onClick
    )
}

@Composable
fun DeleteButtonCancelDialog(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DeleteButtonDialog(
        modifier = modifier,
        textRes = R.string.cancel_dialog_general_text,
        buttonColor = MaterialTheme.colorScheme.secondaryContainer,
        textColor = MaterialTheme.colorScheme.onSecondaryContainer,
        onClick = onClick
    )
}

@Composable
fun DeleteButtonDialog(
    textRes: Int,
    buttonColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(size = 100.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp),
    ) {
        Text(
            color = textColor,
            text = stringResource(textRes),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun DeleteListDialogPreviewLight() {
    ShoppingListTheme(false) {
        DeleteListDialog(listName = TEST_LIST_NAME)
    }
}

@Composable
@Preview(showSystemUi = true)
fun DeleteListDialogPreviewDark() {
    ShoppingListTheme(true) {
        DeleteListDialog(listName = TEST_LIST_NAME)
    }
}