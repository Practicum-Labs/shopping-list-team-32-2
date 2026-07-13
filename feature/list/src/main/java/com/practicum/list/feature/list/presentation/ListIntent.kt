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

    data object ProductMenuEditClicked : ListIntent()

    data object EditProductBottomSheetDismissed : ListIntent()

    data class EditProductConfirmClicked(val product: Product) : ListIntent()


    data object AddProductClicked : ListIntent()
    data object OptionsMenuClicked : ListIntent()
    data class ToggleProductChecked(val productId: Long) : ListIntent()
    data class DeleteProduct(val productId: Long) : ListIntent()
    data class EditProduct(val productId: Long) : ListIntent()
    data class ProductQuantityClicked(val productId: Long) : ListIntent()
}