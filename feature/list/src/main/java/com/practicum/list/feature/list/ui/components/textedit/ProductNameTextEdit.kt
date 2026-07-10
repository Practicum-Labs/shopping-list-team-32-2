package com.practicum.list.feature.list.ui.components.textedit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.practicum.list.feature.list.R
import com.practicum.list.feature.list.ui.components.bottomsheet.textFieldColors

@Composable
fun NameTextEdit(value: String, onValueChanged: (String) -> Unit) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = { Text(stringResource(R.string.goods)) },
        singleLine = true,
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}