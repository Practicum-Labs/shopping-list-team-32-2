package com.practicum.list.feature.list.presentation

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.mvi.MviIntent

sealed class ListIntent : MviIntent {
    data object BackClicked : ListIntent()
    data object DeleteDialogDismissed : ListIntent()
    data class DeleteDialogConfirmed(val type: DeleteType) : ListIntent()
    data object ListMenuCustomSortClicked : ListIntent()
    data object ListMenuAlphabeticalSortClicked : ListIntent()
    data object ListMenuDeleteCheckedClicked : ListIntent()
    data object ListMenuDeleteAllClicked : ListIntent()
    data class ListMenuCustomSortConfirmed(val newList: List<Product>) : ListIntent()
    data class DeleteProductClicked(val productId: Long) : ListIntent()
    data class ProductContextMenuOpened(val product: Product) : ListIntent()
    data class ProductMenuEditClicked(val product: Product) : ListIntent()
    data object EditProductBottomSheetDismissed : ListIntent()
    data class EditProductConfirmClicked(val product: Product) : ListIntent()
    data object OptionsMenuClicked : ListIntent()

    data object OptionsMenuDismissed : ListIntent()
    data class EditProduct(val productId: Long) : ListIntent()

    data class ProductQuantityClicked(val productId: Long) : ListIntent()
    data class ToggleProductChecked(val product: Product, val isChecked: Boolean) : ListIntent()
    data class QuantityChanged(val product: Product, val quantity: Float) : ListIntent()
    data object AddProductClicked : ListIntent()
    data object AddProductDismissClicked : ListIntent()
    data class AddProductNameChanged(val name: String) : ListIntent()
    data class AddProductQuantityChanged(val quantity: Float) : ListIntent()
    data class AddProductUnitChanged(val unit: MeasureUnit) : ListIntent()
    data class AddProductApplyClicked(val product: Product) : ListIntent()
}