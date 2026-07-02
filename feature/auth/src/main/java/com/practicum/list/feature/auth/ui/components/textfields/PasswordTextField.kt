package com.practicum.list.feature.auth.ui.components.textfields

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R

private const val PREVIEW_PASSWORD_LABEL = "Пароль"
private const val PREVIEW_PASSWORD_ERROR = "Пароль менее 7 символов"
private val PREVIEW_FIELD_PADDING = Modifier.padding(16.dp)

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    AuthOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        isError = isError,
        errorText = errorText,
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            PasswordVisibilityToggle(
                isVisible = isPasswordVisible,
                onToggle = { isPasswordVisible = !isPasswordVisible },
            )
        },
    )
}

@Composable
private fun PasswordVisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit,
) {
    val description = if (isVisible) {
        stringResource(R.string.auth_toggle_password_hide)
    } else {
        stringResource(R.string.auth_toggle_password_visibility)
    }
    IconButton(onClick = onToggle) {
        Icon(
            painter = painterResource(
                if (isVisible) R.drawable.ic_eye_off else R.drawable.ic_eye,
            ),
            contentDescription = description,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(name = "Light default", showBackground = true)
@Composable
private fun PasswordTextFieldLightDefaultPreview() {
    ShoppingListTheme(darkTheme = false) {
        PasswordTextField(
            value = "",
            onValueChange = {},
            label = PREVIEW_PASSWORD_LABEL,
            modifier = PREVIEW_FIELD_PADDING,
        )
    }
}

@Preview(name = "Dark default", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordTextFieldDarkDefaultPreview() {
    ShoppingListTheme(darkTheme = true) {
        PasswordTextField(
            value = "secret",
            onValueChange = {},
            label = PREVIEW_PASSWORD_LABEL,
            modifier = PREVIEW_FIELD_PADDING,
        )
    }
}

@Preview(name = "Light error", showBackground = true)
@Composable
private fun PasswordTextFieldLightErrorPreview() {
    ShoppingListTheme(darkTheme = false) {
        PasswordTextField(
            value = "123",
            onValueChange = {},
            label = PREVIEW_PASSWORD_LABEL,
            isError = true,
            errorText = PREVIEW_PASSWORD_ERROR,
            modifier = PREVIEW_FIELD_PADDING,
        )
    }
}

@Preview(name = "Dark error", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordTextFieldDarkErrorPreview() {
    ShoppingListTheme(darkTheme = true) {
        PasswordTextField(
            value = "123",
            onValueChange = {},
            label = PREVIEW_PASSWORD_LABEL,
            isError = true,
            errorText = PREVIEW_PASSWORD_ERROR,
            modifier = PREVIEW_FIELD_PADDING,
        )
    }
}
