package com.practicum.list.feature.list.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.list.R

private val QuantityMinWidth = 24.dp

private const val MIN_QUANTITY = 1

@Composable
fun ProductQuantityStepper(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
) {
    val contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    val textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
    val decreaseDescription = stringResource(R.string.product_quantity_decrease_content_description)
    val increaseDescription = stringResource(R.string.product_quantity_increase_content_description)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        IconButton(
            onClick = onDecrease,
            enabled = quantity > MIN_QUANTITY,
            modifier = Modifier.semantics { contentDescription = decreaseDescription },
        ) {
            Text(
                text = "−",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = contentColor,
                    textDecoration = textDecoration,
                ),
            )
        }
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = contentColor,
                textDecoration = textDecoration,
            ),
            modifier = Modifier.widthIn(min = QuantityMinWidth),
            textAlign = TextAlign.Center,
        )
        IconButton(
            onClick = onIncrease,
            modifier = Modifier.semantics { contentDescription = increaseDescription },
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = contentColor,
                    textDecoration = textDecoration,
                ),
            )
        }
    }
}

@Preview(name = "Light default", showBackground = true)
@Composable
private fun ProductQuantityStepperLightPreview() {
    ShoppingListTheme(darkTheme = false) {
        ProductQuantityStepper(
            quantity = 3,
            onIncrease = {},
            onDecrease = {},
        )
    }
}

@Preview(name = "Dark checked", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProductQuantityStepperDarkCheckedPreview() {
    ShoppingListTheme(darkTheme = true) {
        ProductQuantityStepper(
            quantity = 1,
            onIncrease = {},
            onDecrease = {},
            isChecked = true,
        )
    }
}
