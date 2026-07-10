package com.practicum.list.feature.list.presentation

import com.practicum.list.core.mvi.MviIntent

sealed class ListIntent : MviIntent {
    data object BackClicked : ListIntent()
    data object AddProductClicked : ListIntent()
    data object OptionsMenuClicked : ListIntent()
    data class ToggleProductChecked(val productId: Long) : ListIntent()
    data class DeleteProduct(val productId: Long) : ListIntent()
    data class EditProduct(val productId: Long) : ListIntent()
    data class ProductQuantityClicked(val productId: Long) : ListIntent()
}