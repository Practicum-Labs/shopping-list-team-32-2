package com.practicum.list.core.theme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    placeholderTextRes: Int,
    textString: String,
    onTextChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = outlineTextFieldColors()

    Box(modifier = modifier) {
        BasicTextField(
            value = textString,
            onValueChange = onTextChange,
            modifier = Modifier.height(56.dp).width(210.dp),
            singleLine = true,
            interactionSource = interactionSource,
            textStyle = MaterialTheme.typography.bodyLarge
                .copy(color = MaterialTheme.colorScheme.onBackground),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = textString,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = { Text(stringResource(placeholderTextRes)) },
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
fun outlineTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onBackground,
    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
    disabledTextColor = MaterialTheme.colorScheme.onBackground,
    errorTextColor = MaterialTheme.colorScheme.onBackground,
    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    errorContainerColor = MaterialTheme.colorScheme.primary,
    cursorColor = MaterialTheme.colorScheme.secondary,
    errorCursorColor = MaterialTheme.colorScheme.primary,
    selectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.primary,
        backgroundColor = MaterialTheme.colorScheme.background
    ),
    focusedBorderColor = MaterialTheme.colorScheme.secondary,
    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
    disabledBorderColor = MaterialTheme.colorScheme.primary,
    errorBorderColor = MaterialTheme.colorScheme.primary,
    focusedLabelColor = MaterialTheme.colorScheme.secondary,
    unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
    disabledLabelColor = MaterialTheme.colorScheme.secondary,
    errorLabelColor = MaterialTheme.colorScheme.primary,
    focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
    disabledPlaceholderColor = MaterialTheme.colorScheme.primary,
    errorPlaceholderColor = MaterialTheme.colorScheme.primary,
)