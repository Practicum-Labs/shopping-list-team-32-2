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
        ListIntent.ListMenuAlphabeticalSortClicked,
        ListIntent.ListMenuCustomSortClicked,
        ListIntent.ListMenuDeleteCheckedClicked,
        ListIntent.ListMenuDeleteAllClicked,
        is ListIntent.ListMenuCustomSortConfirmed,
            -> reduceListMenu(intent, current)

        is ListIntent.DeleteDialogConfirmed,
        ListIntent.DeleteDialogDismissed,
            -> reduceDeleteDialog(intent, current)

        is ListIntent.ProductContextMenuOpened -> current.copy(
            contextMenuState = ListContextMenuState()
        )

        is ListIntent.ProductMenuEditClicked -> {
            val product = intent.product
            current.copy(
                productBottomSheetOpened = true,
                productBottomSheetState = ProductBottomSheetState(
                    id = product.id,
                    name = product.name,
                    quantity = product.quantity,
                    unit = product.unit,
                    isChecked = product.isChecked,
                    sortPosition = product.sortPosition,
                ),
            )
        }

        ListIntent.ProductBottomSheetDismissed,
        is ListIntent.DeleteProductClicked,
        is ListIntent.ProductApplyClicked -> current.copy(productBottomSheetOpened = false)

        ListIntent.BackClicked -> current
        ListIntent.AddProductClicked -> current.copy(
            productBottomSheetOpened = true,
            productBottomSheetState = ProductBottomSheetState(),
        )

        is ListIntent.ProductQuantityChanged -> current.copy(
            productBottomSheetState = current.productBottomSheetState.copy(
                quantity = intent.quantity
            )
        )

        is ListIntent.ProductUnitChanged -> current.copy(
            productBottomSheetState = current.productBottomSheetState.copy(
                unit = intent.unit
            )
        )

        is ListIntent.ProductNameChanged -> current.copy(
            productBottomSheetState = current.productBottomSheetState.copy(
                name = intent.name
            )
        )

        else -> current
    }

    private fun reduceListMenu(intent: ListIntent, current: ListState): ListState = when (intent) {
        ListIntent.OptionsMenuClicked -> current.copy(contextMenuOpened = true)
        ListIntent.OptionsMenuDismissed -> current.copy(contextMenuOpened = false)

        ListIntent.ListMenuAlphabeticalSortClicked -> if (current.products.isNotEmpty()) {
            current.copy(
                contextMenuState = ListContextMenuState(sortType = SortType.Alphabetical),
                contextMenuOpened = false
            )
        } else {
            current.copy(contextMenuOpened = false)
        }

        ListIntent.ListMenuCustomSortClicked -> if (current.products.isNotEmpty()) {
            current.copy(
                contextMenuState = ListContextMenuState(sortType = SortType.Custom),
                isBeingSorted = true,
            )
        } else {
            current.copy(contextMenuOpened = false)
        }


        ListIntent.ListMenuDeleteCheckedClicked -> if (current.products.isNotEmpty()) {
            current.copy(
                contextMenuState = null,
                confirmationDialogState = ConfirmationDialogState(deleteType = DeleteType.Checked),

                )
        } else {
            current.copy(contextMenuOpened = false)
        }

        ListIntent.ListMenuDeleteAllClicked -> if (current.products.isNotEmpty()) {
            current.copy(
                contextMenuState = null,
                confirmationDialogState = ConfirmationDialogState(deleteType = DeleteType.All),
            )
        } else {
            current.copy(contextMenuOpened = false)
        }

        is ListIntent.ListMenuCustomSortConfirmed -> if (current.products.isNotEmpty()) {
            current.copy(isBeingSorted = false)
        } else {
            current.copy(contextMenuOpened = false)
        }

        else -> current
    }

    private fun reduceDeleteDialog(intent: ListIntent, current: ListState): ListState =
        when (intent) {
            is ListIntent.DeleteDialogConfirmed -> current.copy(
                confirmationDialogState = null,
                contextMenuOpened = false
            )

            is ListIntent.DeleteDialogDismissed -> current.copy(confirmationDialogState = null)
            else -> current
        }

    override suspend fun handleIntent(intent: ListIntent) {
        when (intent) {
            ListIntent.BackClicked -> emitEffect(ListEffect.NavigateToMain)
            ListIntent.ListMenuCustomSortClicked,
            ListIntent.ListMenuDeleteAllClicked,
            ListIntent.ListMenuDeleteCheckedClicked -> if (state.value.products.isEmpty()) {
                emitEffect(ListEffect.ShowError(EMPTY_LIST_ERROR))
            }

            is ListIntent.DeleteDialogConfirmed -> {
                when (intent.type) {
                    DeleteType.All -> deleteAllItems()
                    DeleteType.Checked -> deleteCheckedItems()
                }
            }

            is ListIntent.ListMenuAlphabeticalSortClicked ->if (state.value.products.isEmpty()) {
                emitEffect(ListEffect.ShowError(EMPTY_LIST_ERROR))
            } else {
                sortAlphabetically()
            }
            is ListIntent.ListMenuCustomSortConfirmed -> sortCustom(intent.newList)
            is ListIntent.ToggleProductChecked -> upsertProduct(
                intent.product.copy(isChecked = intent.isChecked),
            )

            is ListIntent.QuantityChanged -> upsertProduct(
                intent.product.copy(quantity = intent.quantity.coerceAtLeast(MIN_PRODUCT_QUANTITY)),
            )

            is ListIntent.DeleteProductClicked -> deleteProduct(intent.productId)
            ListIntent.ProductApplyClicked -> applyProductForm()

            else -> Unit
        }
    }

    private suspend fun applyProductForm() {
        val bottomSheetState = state.value.productBottomSheetState
        val product = Product(
            id = bottomSheetState.id,
            name = bottomSheetState.name.trim(),
            isChecked = bottomSheetState.isChecked,
            listId = listId,
            quantity = bottomSheetState.quantity.coerceAtLeast(MIN_PRODUCT_QUANTITY),
            unit = bottomSheetState.unit,
            sortPosition = if (state.value.productBottomSheetState.id == 0L) {
                (state.value.products.maxOfOrNull { it.sortPosition } ?: -1) + 1
            } else {
                bottomSheetState.sortPosition
            },
        )
        if (product.quantity == ERROR_QUANTITY) {
            emitEffect(ListEffect.ShowError(QUANTITY_ERROR))
        } else {
            upsertProduct(product)
        }
        updateState {
            it.copy(productBottomSheetState = ProductBottomSheetState())
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
        private const val EMPTY_LIST_ERROR = "Список товаров пуст"
        private const val ERROR_QUANTITY = 0f
        private const val MIN_PRODUCT_QUANTITY = 1f

        private fun createInitialState(handle: SavedStateHandle): ListState {
            val route = handle.toRoute<ListScreenRoute>()
            return ListState(listId = route.listId)
        }
    }
}
