package com.practicum.list.feature.main.presentation

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.mvi.MviState

data class MainState(
    val lists: List<ShoppingList> = emptyList(),
    val isLoading: Boolean = true,
    val createListDialog: CreateListDialogState? = null,
    val selectedListIdForIcon: Long? = null,
    val renameListDialog: RenameListDialogState? = null,
    val deleteListDialog: DeleteListDialogState? = null,
    val isLogoutDialogVisible: Boolean = false,
) : MviState {
    val isEmpty: Boolean get() = lists.isEmpty()
}

data class CreateListDialogState(val name: String = "")
data class RenameListDialogState(val id: Long, val currentName: String, val newName: String) {
    val isEnabled: Boolean get() = newName != currentName && newName.isNotBlank()
}

data class DeleteListDialogState(val id: Long, val name: String)
