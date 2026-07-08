package com.practicum.list.feature.list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.core.navigation.ListScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    MviViewModel<ListIntent, ListState, ListEffect>(
        initialState = createInitialState(
            savedStateHandle
        )
    ) {
    private val listId: Long = savedStateHandle.toRoute<ListScreenRoute>().listId
    override fun reduce(intent: ListIntent, current: ListState): ListState {
        TODO("Not yet implemented")
    }

    override suspend fun handleIntent(intent: ListIntent) {
        when (intent) {
            ListIntent.BackClicked -> emitEffect(ListEffect.NavigateToMain)
        }
    }

    companion object {
        private fun createInitialState(handle: SavedStateHandle): ListState {
            val route = handle.toRoute<ListScreenRoute>()
            return ListState(listId = route.listId)
        }
    }
}