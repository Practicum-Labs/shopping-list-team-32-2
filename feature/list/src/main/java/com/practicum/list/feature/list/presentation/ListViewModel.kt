package com.practicum.list.feature.list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.feature.list.domain.usecase.ObserveListTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ListViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    MviViewModel<ListIntent, ListState, ListEffect>(
        initialState = createInitialState(
            savedStateHandle
        )
    ) {
    private val listId: Long = savedStateHandle.toRoute<ListScreenRoute>().listId


    init {
        viewModelScope.launch {
            observeListTitleUseCase(listId).collect { title ->
                updateState { it.copy(listTitle = title) }
            }
        }
    }

    override fun reduce(intent: ListIntent, current: ListState): ListState = when (intent) {
        ListIntent.BackClicked -> current
        ListIntent.LoadList -> current
        ListIntent.ConfirmDeleteListItems(0L) -> current
        ListIntent.ItemMoved(0L,1) -> current
        ListIntent.AddButtonClicked -> current
        ListIntent.AddProductConfirmed -> current.copy(
            addProductDialogState = null
        )
        ListIntent.AddProductNameChanged("") -> current
        ListIntent.AddProductMinusClicked -> current
        ListIntent.AddProductPlusClicked -> current
        ListIntent.AddProductQuantityChanged(0F) -> current
        ListIntent.AddProductUnitsChanged(MeasureUnit.all.first()) -> current
        ListIntent.AlphabeticalSortClicked(0L) -> current.copy(
            contextMenuState = ListContextMenuState(
                sortType = SortType.Alphabetical
            )
        )
        ListIntent.CustomSortClicked(0L) -> current.copy(
            contextMenuState = ListContextMenuState(
                sortType = SortType.Custom
            )
        )
        ListIntent.DeleteCheckedProductsDialogDismissed -> current.copy(
            confirmationDialogState = null
        )
        ListIntent.DeleteCheckedProductsDialogConfirmed(0L) -> current.copy(
            confirmationDialogState = ConfirmationDialogState(
                deleteType = DeleteType.Checked
            )
        )
        ListIntent.DeleteDialogConfirmed(0L) -> current.copy(
            confirmationDialogState = ConfirmationDialogState(
                deleteType = DeleteType.All
            )
        )
        ListIntent.DeleteDialogDismissed -> current.copy(
            confirmationDialogState = null
        )
        ListIntent.UpdateQuantityAndUnits(0L) -> current
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
            ListIntent.LoadList -> Unit
            ListIntent.ConfirmDeleteListItems(0L) -> Unit
            ListIntent.ItemMoved(0L,1) -> Unit
            ListIntent.AddButtonClicked -> Unit
            ListIntent.AddProductConfirmed -> addProduct()
            ListIntent.AddProductNameChanged("") -> Unit
            ListIntent.AddProductMinusClicked -> Unit
            ListIntent.AddProductPlusClicked -> Unit
            ListIntent.AddProductQuantityChanged(0F) -> Unit
            ListIntent.AddProductUnitsChanged(MeasureUnit.all.first()) -> Unit
            ListIntent.UpdateQuantityAndUnits(0L) -> Unit
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

    private suspend fun addProduct() {
        runCatching {  }
    }

    companion object {
        private fun createInitialState(handle: SavedStateHandle): ListState {
            val route = handle.toRoute<ListScreenRoute>()
            return ListState(listId = route.listId)
        }
    }
}