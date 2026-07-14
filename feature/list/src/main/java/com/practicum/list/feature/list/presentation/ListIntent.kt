package com.practicum.list.feature.list.presentation

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.mvi.MviIntent

sealed class ListIntent : MviIntent {
    data object BackClicked : ListIntent()
    data object DeleteDialogDismissed : ListIntent()
    data class DeleteDialogConfirmed(val type: DeleteType) : ListIntent()
    data object OptionsMenuClicked : ListIntent()
    data object OptionsMenuDismissed : ListIntent()
    data object ListMenuCustomSortClicked : ListIntent()
    data object ListMenuAlphabeticalSortClicked : ListIntent()
    data object ListMenuDeleteCheckedClicked : ListIntent()
    data object ListMenuDeleteAllClicked : ListIntent()
    data class ListMenuCustomSortConfirmed(val newList: List<Product>) : ListIntent()
    data class DeleteProductClicked(val productId: Long) : ListIntent()
    data class ProductContextMenuOpened(val product: Product) : ListIntent()
    data class ProductMenuEditClicked(val product: Product) : ListIntent()
    data object ProductBottomSheetDismissed : ListIntent()
    data class ProductQuantityClicked(val productId: Long) : ListIntent()
    data class ToggleProductChecked(val product: Product, val isChecked: Boolean) : ListIntent()
    data class QuantityChanged(val product: Product, val quantity: Float) : ListIntent()
    data object AddProductClicked : ListIntent()
    data class ProductNameChanged(val name: String) : ListIntent()
    data class ProductQuantityChanged(val quantity: Float) : ListIntent()
    data class ProductUnitChanged(val unit: MeasureUnit) : ListIntent()
    data object ProductApplyClicked : ListIntent()
}
