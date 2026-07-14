package com.practicum.list.core.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.R
import com.practicum.list.core.theme.ShoppingListTheme

@Composable
fun LogoutDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    CustomLayoutDialog(
        modifier = modifier,
        onDismiss = onDismiss,
        centerActions = true,
        title = { LogoutDialogTitle() },
        confirmButton = {
            DeleteButtonDialog(
                textRes = R.string.logout_confirm,
                buttonColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                onClick = onConfirm,
            )
        },
        dismissButton = { buttonModifier ->
            DeleteButtonCancelDialog(
                modifier = buttonModifier,
                onClick = onDismiss,
            )
        },
    )
}

@Composable
fun LogoutDialogTitle() {
    Text(
        modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
        textAlign = TextAlign.Center,
        text = stringResource(R.string.logout_dialog_title),
        style = MaterialTheme.typography.headlineLarge
            .copy(color = MaterialTheme.colorScheme.onSurface),
    )
}

@Composable
@Preview(showSystemUi = true)
fun LogoutDialogPreviewLight() {
    ShoppingListTheme(false) {
        LogoutDialog()
    }
}

@Composable
@Preview(showSystemUi = true)
fun LogoutDialogPreviewDark() {
    ShoppingListTheme(true) {
        LogoutDialog()
    }
}
