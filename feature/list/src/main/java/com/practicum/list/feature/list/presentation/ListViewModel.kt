package com.practicum.list.feature.list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.common.domain.usecase.ObserveListTitleUseCase
import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.feature.list.domain.usecase.DeleteAllProductsUseCase
import com.practicum.list.feature.list.domain.usecase.DeleteBoughtProductsUseCase
import com.practicum.list.feature.list.domain.usecase.DeleteProductUseCase
import com.practicum.list.feature.list.domain.usecase.ObserveProductsByListIdUseCase
import com.practicum.list.feature.list.domain.usecase.SortProductsAlphabeticallyUseCase
import com.practicum.list.feature.list.domain.usecase.SortProductsCustomUseCase
import com.practicum.list.feature.list.domain.usecase.UpsertProductUseCase
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
    private val upsertProductUseCase: UpsertProductUseCase,
) : MviViewModel<ListIntent, ListState, ListEffect>(
    initialState = createInitialState(savedStateHandle),
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
        ListIntent.OptionsMenuClicked,
        ListIntent.OptionsMenuDismissed,
        -> reduceOptionsMenu(intent, current)

        is ListIntent.EditProduct,
        is ListIntent.ProductQuantityClicked,
        -> reduceOpenEditSheet(intent, current)

        ListIntent.ListMenuAlphabeticalSortClicked,
        ListIntent.ListMenuCustomSortClicked,
        ListIntent.ListMenuDeleteCheckedClicked,
        ListIntent.ListMenuDeleteAllClicked,
        is ListIntent.ListMenuCustomSortConfirmed,
        -> reduceListMenu(intent, current)

        is ListIntent.DeleteDialogConfirmed,
        ListIntent.DeleteDialogDismissed,
        -> reduceDeleteDialog(current)

        is ListIntent.ProductContextMenuOpened,
        ListIntent.ProductMenuEditClicked,
        ListIntent.EditProductBottomSheetDismissed,
        is ListIntent.EditProductConfirmClicked,
        is ListIntent.DeleteProductClicked,
        -> reduceProductEdit(intent, current)

        ListIntent.BackClicked -> current
        ListIntent.AddProductClicked -> current.copy(addProductBottomSheetOpened = true)
        ListIntent.AddProductDismissClicked -> current.copy(addProductBottomSheetOpened = false)
        is ListIntent.AddProductQuantityChanged -> current.copy(
            addProductBottomSheetState = current.addProductBottomSheetState.copy(
                quantity = intent.quantity
            )
        )

        is ListIntent.AddProductUnitChanged -> current.copy(
            addProductBottomSheetState = current.addProductBottomSheetState.copy(
                unit = intent.unit
            )
        )

        is ListIntent.AddProductNameChanged -> current.copy(
            addProductBottomSheetState = current.addProductBottomSheetState.copy(
                name = intent.name
            )
        )

        is ListIntent.AddProductApplyClicked -> current.copy(
            addProductBottomSheetState = AddProductBottomSheetState(),
            addProductBottomSheetOpened = false
        )

        else -> current
    }

    private fun reduceOptionsMenu(intent: ListIntent, current: ListState): ListState = when (intent) {
        ListIntent.OptionsMenuClicked -> current.copy(isOptionsMenuVisible = true)
        ListIntent.OptionsMenuDismissed -> current.copy(isOptionsMenuVisible = false)
        else -> current
    }

    private fun reduceOpenEditSheet(intent: ListIntent, current: ListState): ListState {
        val productId = when (intent) {
            is ListIntent.EditProduct -> intent.productId
            is ListIntent.ProductQuantityClicked -> intent.productId
            else -> return current
        }
        val product = current.products.find { it.id == productId } ?: return current
        return current.copy(
            editProductBottomSheetState = EditProductBottomSheetState(
                name = product.name,
                productId = product.id,
                quantity = product.quantity,
                measureUnits = product.unit,
            ),
        )
    }

    private fun reduceListMenu(intent: ListIntent, current: ListState): ListState = when (intent) {
        ListIntent.ListMenuAlphabeticalSortClicked -> current.copy(
            contextMenuState = ListContextMenuState(sortType = SortType.Alphabetical),
            isOptionsMenuVisible = false,
        )

        ListIntent.ListMenuCustomSortClicked -> current.copy(
            contextMenuState = ListContextMenuState(sortType = SortType.Custom),
            isBeingSorted = true,
            isOptionsMenuVisible = false,
        )

        ListIntent.ListMenuDeleteCheckedClicked -> current.copy(
            contextMenuState = null,
            confirmationDialogState = ConfirmationDialogState(deleteType = DeleteType.Checked),
            isOptionsMenuVisible = false,
        )

        ListIntent.ListMenuDeleteAllClicked -> current.copy(
            contextMenuState = null,
            confirmationDialogState = ConfirmationDialogState(deleteType = DeleteType.All),
            isOptionsMenuVisible = false,
        )

        is ListIntent.ListMenuCustomSortConfirmed -> current.copy(isBeingSorted = false)
        else -> current
    }

    private fun reduceDeleteDialog(current: ListState): ListState =
        current.copy(confirmationDialogState = null)

    private fun reduceProductEdit(intent: ListIntent, current: ListState): ListState =
        when (intent) {
            is ListIntent.ProductContextMenuOpened -> current.copy(
                editProductMenuState = EditProductMenuState(product = intent.product),
            )

            ListIntent.ProductMenuEditClicked -> {
                val product = current.editProductMenuState?.product ?: return current
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

            ListIntent.EditProductBottomSheetDismissed,
            is ListIntent.EditProductConfirmClicked,
                -> current.copy(editProductBottomSheetState = null)

            is ListIntent.DeleteProductClicked -> current.copy(editProductMenuState = null)

            else -> current
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
            is ListIntent.EditProductConfirmClicked -> updateProduct(intent.product)
            is ListIntent.ToggleProductChecked -> upsertProduct(
                intent.product.copy(isChecked = intent.isChecked),
            )

            is ListIntent.QuantityChanged -> upsertProduct(
                intent.product.copy(quantity = intent.quantity.coerceAtLeast(MIN_PRODUCT_QUANTITY)),
            )

            is ListIntent.DeleteProductClicked -> deleteProduct(intent.productId)
            is ListIntent.AddProductApplyClicked -> addProduct(intent)

            else -> Unit
        }
    }

    private suspend fun updateProduct(updatedProduct: Product) {
        val existing = state.value.products.find { it.id == updatedProduct.id } ?: updatedProduct
        upsertProduct(
            existing.copy(
                name = updatedProduct.name.trim(),
                quantity = updatedProduct.quantity.coerceAtLeast(MIN_PRODUCT_QUANTITY),
                unit = updatedProduct.unit,
            ),
        )
    }

    private suspend fun addProduct(intent: ListIntent.AddProductApplyClicked) {
        val product = intent.product.copy(
            listId = listId,
            sortPosition = (state.value.products.maxOfOrNull { it.sortPosition } ?: -1) + 1
        )
        if (product.quantity == ERROR_QUANTITY) {
            emitEffect(ListEffect.ShowError(QUANTITY_ERROR))
        } else {
            upsertProduct(product)
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
        private const val QUANTITY_ERROR = "Количество должно быть больше нуля"
        private const val ERROR_QUANTITY = 0f
        private const val MIN_PRODUCT_QUANTITY = 1f

        private fun createInitialState(handle: SavedStateHandle): ListState {
            val route = handle.toRoute<ListScreenRoute>()
            return ListState(listId = route.listId)
        }
    }
}
