package com.practicum.list.feature.auth.ui.components.textfields

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.ShoppingListTheme

private const val PREVIEW_EMAIL_LABEL = "Email"
private const val PREVIEW_EMAIL_ERROR = "Некорректный email"
private val PREVIEW_FIELD_PADDING = Modifier.padding(16.dp)

@Composable
fun AuthOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            isError = isError,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = trailingIcon,
            colors = authTextFieldColors(),
        )
        AnimatedVisibility(
            visible = !errorText.isNullOrBlank(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Text(
                text = errorText.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            )
        }
    }
}

@Composable
internal fun authTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.secondary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    errorBorderColor = MaterialTheme.colorScheme.error,
    focusedLabelColor = MaterialTheme.colorScheme.secondary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    errorLabelColor = MaterialTheme.colorScheme.error,
    cursorColor = MaterialTheme.colorScheme.secondary,
    errorCursorColor = MaterialTheme.colorScheme.error,
)

@Preview(name = "Light default", showBackground = true)
@Composable
private fun AuthOutlinedTextFieldLightDefaultPreview() {
    ShoppingListTheme(darkTheme = false) {
        AuthOutlinedTextField(
            value = "",
            onValueChange = {},
            label = PREVIEW_EMAIL_LABEL,
            modifier = PREVIEW_FIELD_PADDING,
        )
    }
}

@Preview(name = "Dark default", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthOutlinedTextFieldDarkDefaultPreview() {
    ShoppingListTheme(darkTheme = true) {
        AuthOutlinedTextField(
            value = "user@mail.com",
            onValueChange = {},
            label = PREVIEW_EMAIL_LABEL,
            modifier = PREVIEW_FIELD_PADDING,
        )
    }
}

@Preview(name = "Light error", showBackground = true)
@Composable
private fun AuthOutlinedTextFieldLightErrorPreview() {
    ShoppingListTheme(darkTheme = false) {
        AuthOutlinedTextField(
            value = "bad",
            onValueChange = {},
            label = PREVIEW_EMAIL_LABEL,
            isError = true,
            errorText = PREVIEW_EMAIL_ERROR,
            modifier = PREVIEW_FIELD_PADDING,
        )
    }
}

@Preview(name = "Dark error", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthOutlinedTextFieldDarkErrorPreview() {
    ShoppingListTheme(darkTheme = true) {
        AuthOutlinedTextField(
            value = "bad",
            onValueChange = {},
            label = PREVIEW_EMAIL_LABEL,
            isError = true,
            errorText = PREVIEW_EMAIL_ERROR,
            modifier = PREVIEW_FIELD_PADDING,
        )
    }
}
