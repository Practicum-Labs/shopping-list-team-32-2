package com.practicum.list.core.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.practicum.list.core.theme.R
import com.practicum.list.core.theme.ShoppingListTheme

@Composable
fun LoginTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonText: String,
    buttonStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    val textColor = if (isPressed) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
    } else {
        MaterialTheme.colorScheme.primary
    }

    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = true,
        interactionSource = interactionSource,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = textColor,
            disabledContainerColor = Color.Transparent,
        ),
    ) {
        Text(text = buttonText, style = buttonStyle)
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginTextButtonPreview() {
    ShoppingListTheme {
        LoginTextButton(buttonText = stringResource(R.string.new_list_dialog_create_button_text))
    }
}