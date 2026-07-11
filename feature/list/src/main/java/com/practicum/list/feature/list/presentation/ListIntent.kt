package com.practicum.list.feature.list.presentation

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.mvi.MviIntent

sealed class ListIntent : MviIntent {
    data object BackClicked : ListIntent()

    data object ConfirmDeleteListItems : ListIntent()

    data object DeleteDialogDismissed: ListIntent()

    data object DeleteDialogConfirmed: ListIntent()

    data object DeleteCheckedProductsDialogDismissed: ListIntent()

    data object DeleteCheckedProductsDialogConfirmed: ListIntent()

    data class ListMenuCustomSortClicked(val listId: Long): ListIntent()

    data class ListMenuAlphabeticalSortClicked(val listId: Long): ListIntent()

    data object ListMenuDeleteCheckedClicked: ListIntent()

    data object ListMenuDeleteAllClicked: ListIntent()

    data class ListMenuCustomSortConfirmed(val newList: List<Product>): ListIntent()

    data class ItemMoved(val productId: Long, val sortPosition: Int): ListIntent()

    data object AddButtonClicked: ListIntent()

    data class AddProductNameChanged(val name: String) : ListIntent()

    data object AddProductPlusClicked : ListIntent()

    data object AddProductMinusClicked: ListIntent()

    data class AddProductQuantityChanged(val quantity: Float) : ListIntent()

    data class AddProductUnitsChanged(val unit: MeasureUnit) : ListIntent()

    data class AddProductConfirmed(val product: Product): ListIntent()

    data class DeleteProductClicked(val productId: Long): ListIntent()

    data class EditProductClicked(val product: Product): ListIntent()

    data class EditProductConfirmClicked(val product: Product): ListIntent()


    data object AddProductClicked : ListIntent()
    data object OptionsMenuClicked : ListIntent()
    data class ToggleProductChecked(val productId: Long) : ListIntent()
    data class DeleteProduct(val productId: Long) : ListIntent()
    data class EditProduct(val productId: Long) : ListIntent()
    data class ProductQuantityClicked(val productId: Long) : ListIntent()
}