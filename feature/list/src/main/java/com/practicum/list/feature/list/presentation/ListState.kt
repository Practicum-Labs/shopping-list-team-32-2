package com.practicum.list.feature.list.presentation

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.mvi.MviState

data class ListState(
    val listId: Long = 0L,
    val listTitle: String = "",
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val addProductBottomSheetOpened: Boolean = false,
    val addProductBottomSheetState: AddProductBottomSheetState = AddProductBottomSheetState(),
    val addProductError: String? = null,
    val editProductBottomSheetState: EditProductBottomSheetState? = null,
    val contextMenuState: ListContextMenuState? = null,
    val confirmationDialogState: ConfirmationDialogState? = null,
    val editProductMenuState: EditProductMenuState? = null,
    val isBeingSorted: Boolean = false,
) : MviState {
    val isEmpty: Boolean get() = products.isEmpty() && !isLoading
    val isAddEnabled: Boolean
        get() = addProductBottomSheetState.name.trim().isNotEmpty()
}

data class AddProductBottomSheetState(
    val name: String = "",
    val quantity: Float = 1f,
    val unit: MeasureUnit? = MeasureUnit.Package
)

data class EditProductBottomSheetState(
    val name: String,
    val productId: Long,
    val quantity: Float,
    val measureUnits: MeasureUnit
)

data class ListContextMenuState(
    val sortType: SortType,
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