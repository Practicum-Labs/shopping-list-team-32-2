package com.practicum.list.feature.list.ui.components.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.practicum.list.feature.list.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBottomSheet(
    bottomSheetIsVisible: Boolean,
    textValue: String,
    onValueChange: (String) -> Unit,
    amount: String,
    onAmountChange: (String) -> Unit,
    measure: String,
    onMeasureClick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(bottomSheetIsVisible) }

    LaunchedEffect(bottomSheetIsVisible) {
        showBottomSheet = bottomSheetIsVisible
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismiss()
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            OutlinedTextField(
                value = textValue,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.goods)) },
                singleLine = true,
                colors = textFieldColors(),
            )

            Row {
                OutlinedTextField(
                    value = amount,
                    onValueChange = onAmountChange,
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(R.string.amount)) },
                    singleLine = true,
                    colors = textFieldColors(),
                )
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = measure,
                        onValueChange = onMeasureClick,
                        label = { Text(stringResource(R.string.measures)) },
                        singleLine = true,
                        colors = textFieldColors(),
                    )
                    CountDropDownMenu(onItemClick = onMeasureClick)
                }
            }
        }
    }
}

@Composable
private fun CountDropDownMenu(onItemClick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        MenuItem(Measure.KG, onItemClick)
        MenuItem(Measure.G, onItemClick)
        MenuItem(Measure.L, onItemClick)
        MenuItem(Measure.MG, onItemClick)
        MenuItem(Measure.ML, onItemClick)
    }
}

@Composable
private fun MenuItem(measure: Measure, onClick: (String) -> Unit) {
    DropdownMenuItem(
        text = { Text(measure.text) },
        onClick = { onClick(measure.text) }
    )
}

enum class Measure(val text: String) {
    KG("кг"),
    G("г"),
    L("л"),
    MG("mg"),
    ML("мл")
}

@Composable
internal fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.secondary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    focusedLabelColor = MaterialTheme.colorScheme.secondary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    cursorColor = MaterialTheme.colorScheme.secondary,
)

@Preview(showSystemUi = true)
@Composable
private fun BottomSheetPreview(){
    AddProductBottomSheet(
        bottomSheetIsVisible = true,
        textValue = "Алёша",
        onValueChange = {},
        amount = "2",
        onAmountChange = {},
        measure = "кг",
        onMeasureClick = {},
        onDismiss = {}
    )
}
