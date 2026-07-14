package com.practicum.list.feature.list.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.practicum.list.core.common.domain.Product
import java.math.BigDecimal
import java.math.RoundingMode

const val DECIMAL_PLACES = 2

@Composable
fun Product.formatQuantityLabel(): String {
    val unitLabel = stringResource(unit.name)
    return "${formatQuantityValue(quantity)} $unitLabel"
}

fun formatQuantityValue(quantity: Float): String {
    return BigDecimal(quantity.toString())
        .setScale(DECIMAL_PLACES, RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString()
}
