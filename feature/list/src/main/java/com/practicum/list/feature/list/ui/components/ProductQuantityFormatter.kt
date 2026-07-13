package com.practicum.list.feature.list.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.practicum.list.core.common.domain.Product

@Composable
fun Product.formatQuantityLabel(): String {
    val unitLabel = stringResource(unit.name)
    return "${formatQuantityValue(quantity)} $unitLabel"
}

private fun formatQuantityValue(quantity: Float): String {
    val longValue = quantity.toLong()
    return if (quantity == longValue.toFloat()) {
        longValue.toString()
    } else {
        quantity.toString()
    }
}
