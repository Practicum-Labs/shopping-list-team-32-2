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

            is MainIntent.RenameListNameChanged -> current.copy(
                renameListDialog = current.renameListDialog?.copy(newName = intent.name)
            )

            is MainIntent.DismissCreateListDialog -> current.copy(createListDialog = null)
            is MainIntent.DismissRenameListDialog -> current.copy(renameListDialog = null)

            is MainIntent.ConfirmCreateList -> current.copy(createListDialog = null)
            is MainIntent.ConfirmRenameList -> current.copy(renameListDialog = null)
            is MainIntent.RenameListClicked -> showRenameDialog(
                id = intent.id,
                name = intent.name,
                current = current
            )

            is MainIntent.DeleteListClicked -> showDeleteDialog(
                id = intent.id,
                name = intent.name,
                current = current
            )

            is MainIntent.ConfirmDeleteList -> current.copy(deleteListDialog = null)
            is MainIntent.DismissDeleteDialog -> current.copy(deleteListDialog = null)
            is MainIntent.EditListIcon -> current.copy(
                selectedListIdForIcon = intent.id
            )

            is MainIntent.DismissEditListIcon -> current.copy(
                selectedListIdForIcon = null
            )

            else -> current
        }

    override suspend fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.LoadLists -> Unit
            is MainIntent.OpenList -> emitEffect(MainEffect.NavigateToList(intent.id))
            is MainIntent.DeleteListClicked -> validateDeleteConfirmation(intent.id)
            is MainIntent.ConfirmDeleteList -> confirmDelete(intent.id)
            is MainIntent.ConfirmRenameList -> confirmRename(intent.id, intent.newName)
            is MainIntent.DuplicateList -> duplicateList(intent.id)
            is MainIntent.ConfirmCreateList -> {
                val name = intent.name.trim()
                if (name.isNotBlank()) {
                    createList(name)
                }
            }

            is MainIntent.RenameListClicked -> validateRenameDialog(intent.id)
            is MainIntent.EditListIcon -> emitEffect(MainEffect.ShowCategoryPicker(intent.id))
            is MainIntent.DismissEditListIcon -> {
                if (intent.resId != 0 && intent.id != null) {
                    updateIcon(intent.id, intent.resId)
                }
                emitEffect(MainEffect.HideCategoryPicker)
            }

            else -> Unit
        }
    }

    private suspend fun validateDeleteConfirmation(id: Long) {
        val list = state.value.lists.find { it.id == id }
        if (list == null) {
            emitEffect(MainEffect.ShowError(ERROR_LIST_NOT_FOUND))
        }
    }

    private fun showDeleteDialog(id: Long, name: String, current: MainState): MainState {
        val list = state.value.lists.find { it.id == id }
        return if (list != null) {
            current.copy(
                deleteListDialog = DeleteListDialogState(
                    id = id,
                    name = name
                )
            )
        } else {
            current
        }
    }

    private suspend fun confirmDelete(id: Long) {
        runCatching { deleteShoppingListUseCase(id) }
            .onFailure { emitEffect(MainEffect.ShowError(it.message ?: ERROR_DELETE_LIST)) }
    }

    private suspend fun validateRenameDialog(id: Long) {
        val list = state.value.lists.find { it.id == id }
        if (list == null) {
            emitEffect(MainEffect.ShowError(ERROR_LIST_NOT_FOUND))
        }
    }

    private fun showRenameDialog(id: Long, name: String, current: MainState): MainState {
        val list = current.lists.find { it.id == id }
        return if (list != null) {
            current.copy(
                renameListDialog = RenameListDialogState(
                    id = id,
                    currentName = name,
                    newName = name
                )
            )
        } else {
            current
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

    private suspend fun updateIcon(id: Long, resId: Int) {
        val list = state.value.lists.find { it.id == id }
        if (list == null) {
            emitEffect(MainEffect.ShowError(ERROR_LIST_NOT_FOUND))
            return
        }
        runCatching { upsertShoppingListUseCase(list.copy(iconResId = resId)) }
            .onFailure { emitEffect(MainEffect.ShowError(it.message ?: ERROR_UPDATE_ICON_LIST)) }
    }

    private suspend fun duplicateList(id: Long) {
        runCatching { duplicateShoppingListUseCase(id) }
            .onFailure { emitEffect(MainEffect.ShowError(it.message ?: ERROR_DUPLICATE_LIST)) }
    }

    companion object {
        private const val ERROR_LIST_NOT_FOUND = "Список не найден"
        private const val ERROR_DELETE_LIST = "Не удалось удалить список"
        private const val ERROR_RENAME_LIST = "Не удалось переименовать список"

        private const val ERROR_UPDATE_ICON_LIST = "Не удалось изменить иконку в списке"
        private const val ERROR_DUPLICATE_LIST = "Не удалось дублировать список"
        private const val ERROR_CREATE_LIST = "Не удалось создать список"
    }
}
