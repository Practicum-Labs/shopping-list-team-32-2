package com.practicum.list.feature.list.presentation

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.mvi.MviIntent

sealed class ListIntent : MviIntent {
    data object BackClicked : ListIntent()

    data object LoadList : ListIntent()

    data class ConfirmDeleteListItems(val id: Long) : ListIntent()

    data class UpdateQuantityAndUnits(val id: Long): ListIntent()

    data object DeleteDialogDismissed: ListIntent()

    data class DeleteDialogConfirmed(val listId: Long): ListIntent()

    data object DeleteCheckedProductsDialogDismissed: ListIntent()

    data class DeleteCheckedProductsDialogConfirmed(val listId: Long): ListIntent()

    data class CustomSortClicked(val listId: Long): ListIntent()

    data class AlphabeticalSortClicked(val listId: Long): ListIntent()

    data class ItemMoved(val productId: Long, val sortPosition: Int): ListIntent()

    data object AddButtonClicked: ListIntent()

    data class AddProductNameChanged(val name: String) : ListIntent()

    data object AddProductPlusClicked : ListIntent()

    data object AddProductMinusClicked: ListIntent()

    data class AddProductQuantityChanged(val quantity: Float) : ListIntent()

    data class AddProductUnitsChanged(val unit: MeasureUnit) : ListIntent()

    data object AddProductConfirmed: ListIntent()



    data object AddProductClicked : ListIntent()
    data object OptionsMenuClicked : ListIntent()
    data class ToggleProductChecked(val productId: Long) : ListIntent()
    data class DeleteProduct(val productId: Long) : ListIntent()
    data class EditProduct(val productId: Long) : ListIntent()
    data class ProductQuantityClicked(val productId: Long) : ListIntent()
}