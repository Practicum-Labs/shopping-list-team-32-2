package com.practicum.list.feature.list.presentation

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.mvi.MviState

data class ListState(
    val listId: Long = 0L,
    val listTitle: String = "",
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val productBottomSheetOpened: Boolean = false,
    val productBottomSheetState: ProductBottomSheetState = ProductBottomSheetState(),
    val addProductError: String? = null,
    val contextMenuOpened: Boolean = false,
    val contextMenuState: ListContextMenuState? = null,
    val confirmationDialogState: ConfirmationDialogState? = null,
    val isBeingSorted: Boolean = false,
    val isOptionsMenuVisible: Boolean = false,
) : MviState {
    val isEmpty: Boolean get() = products.isEmpty() && !isLoading
    val isAddEnabled: Boolean
        get() = productBottomSheetState.name.trim().isNotEmpty()
}

data class ProductBottomSheetState(
    val id: Long = 0L,
    val name: String = "",
    val quantity: Float = 1f,
    val unit: MeasureUnit = MeasureUnit.Piece,
    val isChecked: Boolean = false,
    val sortPosition: Int = 0,
)

data class ListContextMenuState(
    val sortType: SortType? = null,
)

data class EditProductMenuState(
    val product: Product
)

data class ConfirmationDialogState(
    val deleteType: DeleteType,
)

enum class SortType {
    Alphabetical,
    Custom
}

enum class DeleteType {
    All,
    Checked
}
