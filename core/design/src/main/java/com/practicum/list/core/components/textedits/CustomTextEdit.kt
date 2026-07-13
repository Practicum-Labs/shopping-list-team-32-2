package com.practicum.list.core.components.textedits

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.TextFieldShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextEdit(
    modifier: Modifier = Modifier,
    labelTextRes: Int,
    textString: String,
    interactionSource: MutableInteractionSource,
    onKeyboardDone: () -> Unit = {},
    onTextChange: (String) -> Unit
) {
    val colors = outlineTextFieldColors()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = textString,
            onValueChange = onTextChange,
            modifier = Modifier
                .height(56.dp)
                .width(230.dp)
                .focusRequester(focusRequester),
            singleLine = true,
            interactionSource = interactionSource,
            textStyle = MaterialTheme.typography.bodyLarge
                .copy(color = MaterialTheme.colorScheme.onBackground),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
            keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = textString,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = { Text("") },
                colors = colors,
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = true,
                        isError = false,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = TextFieldShape,
                        focusedBorderThickness = 3.dp,
                        unfocusedBorderThickness = if (textString.isEmpty()) 3.dp else 1.dp,
                    )
                },
            )
        }
        CustomEditTextLabel(
            textRes = labelTextRes,
            modifier = Modifier.align(Alignment.TopStart)
        )
    }
}

@Composable
fun CustomEditTextLabel(
    textRes: Int,
    modifier: Modifier
) {
    Text(
        text = stringResource(textRes),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
            .offset(x = 12.dp, y = (-8).dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 4.dp)
    )
}

@Composable
fun outlineTextFieldColors(): TextFieldColors {
    val textColor = MaterialTheme.colorScheme.surfaceBright
    val containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    val borderColor = MaterialTheme.colorScheme.secondary
    return OutlinedTextFieldDefaults.colors(
        focusedTextColor = textColor,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTextColor = textColor,
        errorTextColor = textColor,
        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        disabledContainerColor = containerColor,
        errorContainerColor = containerColor,
        cursorColor = borderColor,
        errorCursorColor = borderColor,
        selectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.background
        ),
        focusedBorderColor = borderColor,
        unfocusedBorderColor = borderColor,
        disabledBorderColor = borderColor,
        errorBorderColor = borderColor,
        focusedLabelColor = MaterialTheme.colorScheme.secondary,
        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
        disabledLabelColor = MaterialTheme.colorScheme.secondary,
        errorLabelColor = MaterialTheme.colorScheme.primary,
        focusedPlaceholderColor = textColor,
        unfocusedPlaceholderColor = textColor,
        disabledPlaceholderColor = textColor,
        errorPlaceholderColor = textColor,
    )
}