package com.practicum.list.feature.main.presentation

import androidx.lifecycle.viewModelScope
import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.feature.main.domain.usecase.DeleteShoppingListUseCase
import com.practicum.list.feature.main.domain.usecase.DuplicateShoppingListUseCase
import com.practicum.list.feature.main.domain.usecase.ObserveShoppingListsUseCase
import com.practicum.list.feature.main.domain.usecase.UpsertShoppingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.practicum.list.core.theme.R as resorces

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeShoppingListsUseCase: ObserveShoppingListsUseCase,
    private val deleteShoppingListUseCase: DeleteShoppingListUseCase,
    private val duplicateShoppingListUseCase: DuplicateShoppingListUseCase,
    private val upsertShoppingListUseCase: UpsertShoppingListUseCase,
) : MviViewModel<MainIntent, MainState, MainEffect>(MainState()) {

    init {
        viewModelScope.launch {
            observeShoppingListsUseCase().collect { lists ->
                updateState { it.copy(lists = lists, isLoading = false) }
            }
        }
    }

    override fun reduce(intent: MainIntent, current: MainState): MainState =
        when (intent) {
            is MainIntent.LoadLists -> if (current.lists.isEmpty()) {
                current.copy(isLoading = true)
            } else {
                current
            }
            is MainIntent.CreateListClicked -> current.copy(createListDialog = CreateListDialogState())
            is MainIntent.CreateListNameChanged -> current.copy(
                createListDialog = current.createListDialog?.copy(name = intent.name),
            )

            is MainIntent.DismissCreateListDialog -> current.copy(createListDialog = null)
            is MainIntent.ConfirmCreateList -> current.copy(createListDialog = null)
            else -> current
        }

    override suspend fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.LoadLists -> Unit
            is MainIntent.OpenList -> emitEffect(MainEffect.NavigateToList(intent.id))
            is MainIntent.DeleteList -> showDeleteConfirmation(intent.id)
            is MainIntent.ConfirmDeleteList -> confirmDelete(intent.id)
            is MainIntent.RenameList -> showRenameDialog(intent.id)
            is MainIntent.ConfirmRenameList -> confirmRename(intent.id, intent.newName)
            is MainIntent.DuplicateList -> duplicateList(intent.id)
            is MainIntent.ConfirmCreateList -> {
                val name = intent.name.trim()
                if (name.isNotBlank()) {
                    createList(name)
                }
            }

            else -> Unit
        }
    }

    private suspend fun showDeleteConfirmation(id: Long) {
        val list = state.value.lists.find { it.id == id }
        if (list != null) {
            emitEffect(MainEffect.ShowDeleteConfirmation(id, list.name))
        } else {
            emitEffect(MainEffect.ShowError(ERROR_LIST_NOT_FOUND))
        }
    }

    private suspend fun confirmDelete(id: Long) {
        runCatching { deleteShoppingListUseCase(id) }
            .onFailure { emitEffect(MainEffect.ShowError(it.message ?: ERROR_DELETE_LIST)) }
    }

    private suspend fun showRenameDialog(id: Long) {
        val list = state.value.lists.find { it.id == id }
        if (list != null) {
            emitEffect(MainEffect.ShowRenameDialog(id, list.name))
        } else {
            emitEffect(MainEffect.ShowError(ERROR_LIST_NOT_FOUND))
        }
    }

    private suspend fun createList(name: String) {
        if (name.isBlank()) return
        val newList = ShoppingList(
            id = 0L,
            name = name,
            iconResId = resorces.drawable.ic_list_cart,
            products = listOf()
        )
        runCatching { upsertShoppingListUseCase(newList) }
            .onFailure { emitEffect(MainEffect.ShowError(it.message ?: ERROR_CREATE_LIST)) }

    }

    private suspend fun confirmRename(id: Long, newName: String) {
        val list = state.value.lists.find { it.id == id }
        if (list == null) {
            emitEffect(MainEffect.ShowError(ERROR_LIST_NOT_FOUND))
            return
        }
        runCatching { upsertShoppingListUseCase(list.copy(name = newName)) }
            .onFailure { emitEffect(MainEffect.ShowError(it.message ?: ERROR_RENAME_LIST)) }
    }

    private suspend fun duplicateList(id: Long) {
        runCatching { duplicateShoppingListUseCase(id) }
            .onFailure { emitEffect(MainEffect.ShowError(it.message ?: ERROR_DUPLICATE_LIST)) }
    }

    companion object {
        private const val ERROR_LIST_NOT_FOUND = "Список не найден"
        private const val ERROR_DELETE_LIST = "Не удалось удалить список"
        private const val ERROR_RENAME_LIST = "Не удалось переименовать список"
        private const val ERROR_DUPLICATE_LIST = "Не удалось дублировать список"
        private const val ERROR_CREATE_LIST = "Не удалось создать список"
    }
}
