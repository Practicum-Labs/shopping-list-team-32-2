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
import com.practicum.list.feature.main.domain.usecase.ObserveShoppingListTitleUseCase
import com.practicum.list.feature.list.domain.usecase.ObserveListTitleUseCase
import com.practicum.list.core.common.domain.usecase.ObserveListTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeProductsByListIdUseCase: ObserveProductsByListIdUseCase,
    private val observeListTitleUseCase: ObserveListTitleUseCase,
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
            addProductDialogState = current.addProductDialogState?.copy(name = intent.name)
                ?: AddProductDialogState(name = intent.name),
        )
        ListIntent.AddProductMinusClicked -> current.addProductDialogState?.let { dialog ->
            current.copy(
                addProductDialogState = dialog.copy(
                    quantity = (dialog.quantity ?: 1f) - 0.1f,
                ),
            )
        } ?: current
        ListIntent.AddProductPlusClicked ->  current.addProductDialogState?.let { dialog ->
            current.copy(
                addProductDialogState = dialog.copy(
                    quantity = (dialog.quantity ?: 1f) + 0.1f,
                ),
            )
        } ?: current
        is ListIntent.AddProductQuantityChanged -> current.copy(
            addProductDialogState = current.addProductDialogState?.copy(quantity = intent.quantity)
                ?: AddProductDialogState(quantity = intent.quantity),
        )
        is ListIntent.AddProductUnitsChanged -> current.copy(
            addProductDialogState = current.addProductDialogState?.copy(unit = intent.unit)
                ?: AddProductDialogState(unit = intent.unit),
        )
        ListIntent.ListMenuAlphabeticalSortClicked -> current.copy(
            contextMenuState = ListContextMenuState(
                sortType = SortType.Alphabetical
            )
        ) // закрываем по тапу вне меню!
        ListIntent.ListMenuCustomSortClicked -> current.copy(
            contextMenuState = ListContextMenuState(
                sortType = SortType.Custom
            ),
            isBeingSorted = true
        ) // закрываем по тапу вне меню!
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
        is ListIntent.DeleteDialogConfirmed -> current.copy(
            confirmationDialogState = null
        )
        ListIntent.DeleteDialogDismissed -> current.copy(
            confirmationDialogState = null
        )
        is ListIntent.ProductContextMenuOpened -> current.copy(
            editProductMenuState = EditProductMenuState(product = intent.product)
        )
        ListIntent.ProductMenuEditClicked -> {
            val product = current.editProductMenuState?.product
            if (product == null) {
                current
            } else {
                current.copy(
                    editProductMenuState = null,
                    editProductBottomSheetState = EditProductBottomSheetState(
                        name = product.name,
                        productId = product.id,
                        quantity = product.quantity,
                        measureUnits = product.unit,
                    ),
                )
            }
        }
        ListIntent.EditProductBottomSheetDismissed -> current.copy(
            editProductBottomSheetState = null
        )
        is ListIntent.EditProductConfirmClicked -> current.copy(
            editProductBottomSheetState = null
        )
        is ListIntent.DeleteProductClicked -> current.copy(
            editProductMenuState = null
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
            is ListIntent.DeleteDialogConfirmed -> {
                when (intent.type) {
                    DeleteType.All -> deleteAllItems()
                    DeleteType.Checked -> deleteCheckedItems()
                }
            }
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
        runCatchingWithError {
            sortProductsCustomUseCase(listId, newOrderedList)
        }
    }

    private suspend fun sortAlphabetically() {
        runCatchingWithError {
            sortProductsAlphabeticallyUseCase(listId)
        }
    }

    private suspend fun deleteCheckedItems() {
        runCatchingWithError {
            deleteBoughtProductsUseCase(listId)
        }
    }

    private suspend fun deleteAllItems() {
        runCatchingWithError {
            deleteAllProductsUseCase(listId)
        }
    }

    private suspend fun upsertProduct(product: Product) {
        runCatchingWithError {
            upsertProductUseCase(product)
        }
    }

    private suspend fun deleteProduct(id: Long) {
        runCatchingWithError {
            deleteProductUseCase(id)
        }
    }

    private suspend fun runCatchingWithError(block: suspend () -> Unit) {
        runCatching { block() }
            .onFailure { error ->
                emitEffect(ListEffect.ShowError(error.message ?: UNKNOWN_ERROR))
            }
    }

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"

        private fun createInitialState(handle: SavedStateHandle): ListState {
            val route = handle.toRoute<ListScreenRoute>()
            return ListState(listId = route.listId)
        }
    }
}