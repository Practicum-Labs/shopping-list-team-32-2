package com.practicum.list.feature.auth.ui.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R

@Composable
fun PrimaryAuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary,
        ),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.secondary,
                strokeWidth = 2.dp,
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview(name = "Light default", showBackground = true)
@Composable
private fun PrimaryAuthButtonLightDefaultPreview() {
    ShoppingListTheme(darkTheme = false) {
        PrimaryAuthButton(
            text = stringResource(R.string.auth_button_login),
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dark default", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrimaryAuthButtonDarkDefaultPreview() {
    ShoppingListTheme(darkTheme = true) {
        PrimaryAuthButton(
            text = stringResource(R.string.auth_button_register),
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Light disabled", showBackground = true)
@Composable
private fun PrimaryAuthButtonLightDisabledPreview() {
    ShoppingListTheme(darkTheme = false) {
        PrimaryAuthButton(
            text = stringResource(R.string.auth_button_login),
            onClick = {},
            enabled = false,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dark disabled", showBackground = true)
@Composable
private fun PrimaryAuthButtonDarkDisabledPreview() {
    ShoppingListTheme(darkTheme = false) {
        PrimaryAuthButton(
            text = stringResource(R.string.auth_button_login),
            onClick = {},
            enabled = false,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dark loading", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrimaryAuthButtonDarkLoadingPreview() {
    ShoppingListTheme(darkTheme = true) {
        PrimaryAuthButton(
            text = stringResource(R.string.auth_button_login),
            onClick = {},
            isLoading = true,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dark loading", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrimaryAuthButtonLightLoadingPreview() {
    ShoppingListTheme(darkTheme = false) {
        PrimaryAuthButton(
            text = stringResource(R.string.auth_button_login),
            onClick = {},
            isLoading = true,
            modifier = Modifier.padding(16.dp),
        )
    }
}
