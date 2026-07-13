package com.practicum.list.feature.list.ui.components.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.practicum.list.feature.list.R
import com.practicum.list.feature.list.ui.components.bottomsheet.Measure
import com.practicum.list.feature.list.ui.components.bottomsheet.textFieldColors
import com.practicum.list.feature.list.ui.components.textedit.CommonTextFieldLabel

@Composable
fun CountDropDownMenu(
    modifier: Modifier = Modifier,
    measure: String,
    onMeasureClick: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = measure,
            onValueChange = onMeasureClick,
            label = {
                CommonTextFieldLabel(R.string.measures)
            },
            colors = textFieldColors(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_dropdown_menu_24),
                        contentDescription = stringResource(R.string.expand_dropdown_menu)
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Measure.entries.forEach { measure ->
                MenuItem(
                    measure,
                    onClick = { selected ->
                        onMeasureClick(selected)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun MenuItem(measure: Measure, onClick: (String) -> Unit) {
    DropdownMenuItem(text = { Text(measure.text) }, onClick = { onClick(measure.text) })
}
