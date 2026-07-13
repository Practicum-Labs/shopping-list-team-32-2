package com.practicum.list.feature.list.ui.components.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.fab.AddFab
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.list.R
import com.practicum.list.feature.list.ui.components.menu.CountDropDownMenu
import com.practicum.list.feature.list.ui.components.quantifier.RoundQuantifier
import com.practicum.list.feature.list.ui.components.textedit.AmountTextField
import com.practicum.list.feature.list.ui.components.textedit.NameTextEdit
import kotlinx.coroutines.delay

private const val KEYBOARD_OPEN_DELAY = 200L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBottomSheet(
    isApplyVisible: Boolean,
    bottomSheetIsVisible: Boolean,
    textValue: String,
    onTextValueChange: (String) -> Unit,
    amount: Float,
    onAmountChange: (Float) -> Unit,
    measure: String,
    onMeasureClick: (String) -> Unit,
    onDismiss: () -> Unit,
    onApplyClicked: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(bottomSheetIsVisible) {
        if (bottomSheetIsVisible) {
            delay(KEYBOARD_OPEN_DELAY)
            focusRequester.requestFocus()
        }
    }

    if (bottomSheetIsVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismiss()
            },
            sheetState = sheetState,
            containerColor = Color.Transparent,
            contentColor = contentColorFor(MaterialTheme.colorScheme.surfaceContainerLow),
            tonalElevation = 0.dp,
            scrimColor = BottomSheetDefaults.ScrimColor,
            dragHandle = null
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                if (isApplyVisible) {
                    AddFab(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 16.dp)
                            .size(56.dp),
                        onClick = onApplyClicked,
                        iconRes = R.drawable.ic_apply_24
                    )
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp)
                        .imePadding(),
                    shape = BottomSheetDefaults.ExpandedShape,
                    color = MaterialTheme.colorScheme.surfaceContainerLow
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BottomSheetDefaults.DragHandle(modifier = Modifier.align(Alignment.CenterHorizontally))

                        NameTextEdit(
                            value = textValue,
                            onValueChanged = onTextValueChange,
                            focusRequester = focusRequester
                        )
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .heightIn(max = 64.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AmountTextField(
                                modifier = Modifier.weight(1f),
                                amount = amount,
                                onAmountChanged = onAmountChange
                            )
                            CountDropDownMenu(
                                modifier = Modifier.weight(1f),
                                measure = measure,
                                onMeasureClick = onMeasureClick
                            )
                            RoundQuantifier(
                                count = amount,
                                onCountChange = onAmountChange
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class Measure(val text: String) {
    KG("кг"), G("г"), L("л"), MG("mg"), ML("мл")
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
private fun BottomSheetFilledPreview() {
    ShoppingListTheme {
        AddProductBottomSheet(
            bottomSheetIsVisible = true,
            textValue = "лабубы",
            onTextValueChange = {},
            amount = 2f,
            onAmountChange = {},
            measure = Measure.KG.text,
            onMeasureClick = {},
            onDismiss = {},
            onApplyClicked = {},
            isApplyVisible = true
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun BottomSheetEmptyPreview() {
    ShoppingListTheme {
        AddProductBottomSheet(
            bottomSheetIsVisible = true,
            textValue = "",
            onTextValueChange = {},
            amount = 2f,
            onAmountChange = {},
            measure = Measure.KG.text,
            onMeasureClick = {},
            onDismiss = {},
            onApplyClicked = {},
            isApplyVisible = true
        )
    }
}
