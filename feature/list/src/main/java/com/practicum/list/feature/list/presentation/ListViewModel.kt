package com.practicum.list.feature.list.presentation



import androidx.lifecycle.SavedStateHandle

import androidx.lifecycle.viewModelScope

import androidx.navigation.toRoute

import com.practicum.list.core.mvi.MviViewModel

import com.practicum.list.core.navigation.ListScreenRoute

import com.practicum.list.feature.list.domain.usecase.ObserveListTitleUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch

import javax.inject.Inject



@HiltViewModel

class ListViewModel @Inject constructor(

    savedStateHandle: SavedStateHandle,

    observeListTitleUseCase: ObserveListTitleUseCase,

) : MviViewModel<ListIntent, ListState, ListEffect>(

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

            ListIntent.AddProductClicked,

            ListIntent.OptionsMenuClicked,

            is ListIntent.ToggleProductChecked,

            is ListIntent.DeleteProduct,
            is ListIntent.EditProduct,
            is ListIntent.ProductQuantityClicked,

            -> Unit

        }

    }



    companion object {

        private fun createInitialState(handle: SavedStateHandle): ListState {

            val route = handle.toRoute<ListScreenRoute>()

            return ListState(listId = route.listId)

        }

    }

}

