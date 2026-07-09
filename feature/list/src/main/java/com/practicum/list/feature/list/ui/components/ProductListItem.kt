package com.practicum.list.feature.list.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.list.R
import com.practicum.list.core.theme.R as CoreR

private const val PREVIEW_PRODUCT_NAME = "Яблоки"
private const val PREVIEW_QUANTITY_LABEL = "1 кг"
private const val PREVIEW_LONG_PRODUCT_NAME =
    "Очень длинное название товара которое не помещается в две строки списка покупок"

@Composable
fun ProductListItem(
    name: String,
    quantityLabel: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onQuantityClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val nameColor = if (isChecked) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val nameDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
    val checkboxDescription = stringResource(R.string.product_checkbox_content_description)
    val quantityDescription = stringResource(R.string.product_quantity_content_description)

    Column(modifier = modifier.fillMaxWidth()) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            leadingContent = {
                ProductRoundCheckbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier.semantics { contentDescription = checkboxDescription },
                )
            },
            headlineContent = {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = nameColor,
                        textDecoration = nameDecoration,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            supportingContent = {
                Text(
                    text = quantityLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .clickable(onClick = onQuantityClick)
                        .semantics { contentDescription = quantityDescription },
                )
            },
            trailingContent = {
                IconButton(onClick = onDelete) {
                    Icon(
                        painter = painterResource(CoreR.drawable.ic_delete_24),
                        contentDescription = stringResource(R.string.product_delete_content_description),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Preview(name = "Light default", showBackground = true)
@Composable
private fun ProductListItemLightDefaultPreview() {
    ShoppingListTheme(darkTheme = false) {
        ProductListItem(
            name = PREVIEW_PRODUCT_NAME,
            quantityLabel = PREVIEW_QUANTITY_LABEL,
            isChecked = false,
            onCheckedChange = {},
            onQuantityClick = {},
            onDelete = {},
        )
    }
}

@Preview(name = "Light checked", showBackground = true)
@Composable
private fun ProductListItemLightCheckedPreview() {
    ShoppingListTheme(darkTheme = false) {
        ProductListItem(
            name = PREVIEW_PRODUCT_NAME,
            quantityLabel = PREVIEW_QUANTITY_LABEL,
            isChecked = true,
            onCheckedChange = {},
            onQuantityClick = {},
            onDelete = {},
        )
    }
}

@Preview(name = "Dark default", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProductListItemDarkDefaultPreview() {
    ShoppingListTheme(darkTheme = true) {
        ProductListItem(
            name = PREVIEW_PRODUCT_NAME,
            quantityLabel = PREVIEW_QUANTITY_LABEL,
            isChecked = false,
            onCheckedChange = {},
            onQuantityClick = {},
            onDelete = {},
        )
    }
}

@Preview(name = "Long product name", showBackground = true)
@Composable
private fun ProductListItemLongNamePreview() {
    ShoppingListTheme(darkTheme = false) {
        ProductListItem(
            name = PREVIEW_LONG_PRODUCT_NAME,
            quantityLabel = "10 кг",
            isChecked = false,
            onCheckedChange = {},
            onQuantityClick = {},
            onDelete = {},
        )
    }
}
