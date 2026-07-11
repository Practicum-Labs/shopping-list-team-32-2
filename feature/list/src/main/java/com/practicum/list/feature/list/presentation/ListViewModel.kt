package com.practicum.list.feature.list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.feature.list.domain.usecase.DeleteAllProductsUseCase
import com.practicum.list.feature.list.domain.usecase.DeleteBoughtProductsUseCase
import com.practicum.list.feature.list.domain.usecase.DeleteProductUseCase
import com.practicum.list.feature.list.domain.usecase.ObserveProductsByListIdUseCase
import com.practicum.list.feature.list.domain.usecase.SortProductsAlphabeticallyUseCase
import com.practicum.list.feature.list.domain.usecase.SortProductsCustomUseCase
import com.practicum.list.feature.list.domain.usecase.UpsertProductUseCase
import com.practicum.list.feature.list.domain.usecase.ObserveListTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeProductsByListIdUseCase: ObserveProductsByListIdUseCase,
    private val deleteAllProductsUseCase: DeleteAllProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val deleteBoughtProductsUseCase: DeleteBoughtProductsUseCase,
    private val sortProductsAlphabeticallyUseCase: SortProductsAlphabeticallyUseCase,
    private val sortProductsCustomUseCase: SortProductsCustomUseCase,
    private val upsertProductUseCase: UpsertProductUseCase
) :
    MviViewModel<ListIntent, ListState, ListEffect>(
        initialState = createInitialState(
            savedStateHandle
        )
    ) {
    private val listId: Long = savedStateHandle.toRoute<ListScreenRoute>().listId

    init {
        viewModelScope.launch {
            observeProductsByListIdUseCase(listId).collect { products ->
                updateState { it.copy(products = products, isLoading = false) }
            }
        }

        viewModelScope.launch {
            observeListTitleUseCase(listId).collect { title ->
                updateState { it.copy(listTitle = title) }
            }
        }
    }

    override fun reduce(intent: ListIntent, current: ListState): ListState = when (intent) {
        is ListIntent.ConfirmDeleteListItems -> current.copy(
            confirmationDialogState = null
        )
        is ListIntent.AddButtonClicked -> current.copy(
            addProductDialogState = AddProductDialogState(
                name = "",
                quantity = null,
                unit = null
            )
        )
        is ListIntent.AddProductConfirmed -> current.copy(
            addProductDialogState = null
        )
        is ListIntent.AddProductNameChanged -> current.copy(
            addProductDialogState = AddProductDialogState(
                name = intent.name
            )
        )
        ListIntent.AddProductMinusClicked -> current.copy(
            addProductDialogState = AddProductDialogState(
                quantity = 1.1F// прошлое минус 0,1
            )
        )
        ListIntent.AddProductPlusClicked ->  current.copy(
            addProductDialogState = AddProductDialogState(
                quantity = 1.1F// прошлое плюс 0,1
            )
        )
        is ListIntent.AddProductQuantityChanged -> current.copy(
            addProductDialogState = AddProductDialogState(
                quantity = intent.quantity
            )
        )
        is ListIntent.AddProductUnitsChanged -> current.copy(
            addProductDialogState = AddProductDialogState(
                unit = intent.unit
            )
        )
        ListIntent.ListMenuAlphabeticalSortClicked(0L) -> current.copy(
            contextMenuState = ListContextMenuState(
                sortType = SortType.Alphabetical
            )
        )
        ListIntent.ListMenuCustomSortClicked(0L) -> current.copy(
            contextMenuState = ListContextMenuState(
                sortType = SortType.Custom
            )
        )
        ListIntent.DeleteCheckedProductsDialogDismissed -> current.copy(
            confirmationDialogState = null
        )
        ListIntent.DeleteCheckedProductsDialogConfirmed -> current.copy(
            confirmationDialogState = null
        )
        ListIntent.ListMenuDeleteCheckedClicked -> current.copy(
            contextMenuState = null,
            confirmationDialogState = ConfirmationDialogState(
                deleteType = DeleteType.Checked
            )
        )
        ListIntent.ListMenuDeleteAllClicked -> current.copy(
            contextMenuState = null,
            confirmationDialogState = ConfirmationDialogState(
                deleteType = DeleteType.All
            )
        )
        ListIntent.DeleteDialogConfirmed -> current.copy(
            confirmationDialogState = null
        )
        ListIntent.DeleteDialogDismissed -> current.copy(
            confirmationDialogState = null
        )
        is ListIntent.EditProductConfirmClicked -> current.copy(
            editProductBottomSheetState = null
        )
        is ListIntent.DeleteProductClicked -> current.copy(
            editProductMenuState = null
        )
        is ListIntent.EditProductClicked -> current.copy(
            editProductMenuState = EditProductMenuState(product = intent.product)
        )
        is ListIntent.ListMenuCustomSortClicked -> current.copy(
            isBeingSorted = true
        )
        is ListIntent.ListMenuCustomSortConfirmed -> current.copy(
            isBeingSorted = false
        )
        else -> current

        ListIntent.BackClicked,

        ListIntent.AddProductClicked,

        ListIntent.OptionsMenuClicked,

            -> current

        is ListIntent.ToggleProductChecked,

        is ListIntent.DeleteProduct,
        is ListIntent.EditProduct,
        is ListIntent.ProductQuantityClicked,

            -> current
    }

    override suspend fun handleIntent(intent: ListIntent) {
        when (intent) {
            ListIntent.BackClicked -> emitEffect(ListEffect.NavigateToMain)
            ListIntent.ConfirmDeleteListItems -> deleteAllItems()
            ListIntent.DeleteCheckedProductsDialogConfirmed -> deleteCheckedItems()
            ListIntent.ItemMoved(0L,1) -> Unit
            is ListIntent.ListMenuAlphabeticalSortClicked -> sortAlphabetically()
            is ListIntent.ListMenuCustomSortConfirmed -> sortCustom(intent.newList)
            is ListIntent.AddProductConfirmed -> upsertProduct(intent.product)
            is ListIntent.EditProductConfirmClicked -> upsertProduct(intent.product)
            is ListIntent.DeleteProductClicked -> deleteProduct(intent.productId)
            else -> Unit

            ListIntent.AddProductClicked,

            ListIntent.OptionsMenuClicked,

            is ListIntent.ToggleProductChecked,

            is ListIntent.DeleteProduct,
            is ListIntent.EditProduct,
            is ListIntent.ProductQuantityClicked,

                -> Unit
        }
    }

    private suspend fun sortCustom(newOrderedList: List<Product>) {
        runCatching {
            sortProductsCustomUseCase(listId, newOrderedList)
        }
    }


    private suspend fun sortAlphabetically() {
        runCatching {
            sortProductsAlphabeticallyUseCase(listId)
        }
    }

    private suspend fun deleteCheckedItems() {
        runCatching {
            deleteBoughtProductsUseCase(listId)
        }
    }

    private suspend fun deleteAllItems() {
        runCatching {
            deleteAllProductsUseCase(listId)
        }
    }

    private suspend fun upsertProduct(product: Product) {
        runCatching {
           upsertProductUseCase(product)
        }
    }

    private suspend fun deleteProduct(id: Long) {
        runCatching {
            deleteProductUseCase(id)
        }
    }

    companion object {
        private fun createInitialState(handle: SavedStateHandle): ListState {
            val route = handle.toRoute<ListScreenRoute>()
            return ListState(listId = route.listId)
        }
    }
}