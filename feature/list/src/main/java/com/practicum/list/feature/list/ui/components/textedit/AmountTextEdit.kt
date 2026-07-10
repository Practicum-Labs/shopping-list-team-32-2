package com.practicum.list.feature.list.ui.components.textedit

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.practicum.list.feature.list.R
import com.practicum.list.feature.list.ui.components.bottomsheet.textFieldColors
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    amount: Float,
    onAmountChanged: (Float) -> Unit
) {
    var localAmountText by remember { mutableStateOf("") }
    LaunchedEffect(amount) {
        val formatted = if (amount == 0f) "" else formatAmount(amount)
        if (formatted.toFloatOrNull() != localAmountText.toFloatOrNull()) {
            localAmountText = formatted
        }
    }
    OutlinedTextField(
        value = localAmountText,
        onValueChange = { newValue ->
            val filtered = newValue.replace(',', '.')
            if (filtered.count { it == '.' } <= 1 && filtered.all { it.isDigit() || it == '.' }) {
                localAmountText = filtered

                if (filtered.isEmpty() || filtered == ".") {
                    onAmountChanged(0f)
                } else {
                    val cleanValue = if (filtered.endsWith(".")) filtered.dropLast(1) else filtered
                    cleanValue.toFloatOrNull()?.let { parsedFloat ->

                        val preciseFloat = BigDecimal(parsedFloat.toDouble())
                            .setScale(2, RoundingMode.HALF_UP)
                            .toFloat()

                        onAmountChanged(preciseFloat)
                    }
                }
            }
        },
        modifier = modifier,
        label = {
            CommonTextFieldLabel(R.string.amount)
        },
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
    )
}

private fun formatAmount(value: Float): String {
    if (value == 0f) return ""

    val rounded = BigDecimal(value.toDouble())
        .setScale(2, RoundingMode.HALF_UP)
        .stripTrailingZeros()

    return rounded.toPlainString()
}
