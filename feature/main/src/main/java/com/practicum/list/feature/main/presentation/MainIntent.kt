package com.practicum.list.feature.main.presentation

import com.practicum.list.core.mvi.MviIntent

sealed class MainIntent : MviIntent {
    data object LoadLists : MainIntent()
    data class OpenList(val id: Long) : MainIntent()
    data class DuplicateList(val id: Long) : MainIntent()

    // Диалог создания
    data object CreateListClicked : MainIntent()
    data class CreateListNameChanged(val name: String) : MainIntent()
    data class ConfirmCreateList(val name: String) : MainIntent()
    data object DismissCreateListDialog : MainIntent()

    // Диалог переименования
    data class RenameListClicked(val id: Long, val name: String) : MainIntent()
    data class RenameListNameChanged(val name: String) : MainIntent()
    data class ConfirmRenameList(val id: Long, val newName: String) : MainIntent()
    data class EditListIcon(val id: Long) : MainIntent()
    data class DismissEditListIcon(val id: Long?, val resId: Int) : MainIntent()
    data object DismissRenameListDialog : MainIntent()

    // Диалог удаления
    data class DeleteListClicked(val id: Long, val name: String) : MainIntent()
    data class ConfirmDeleteList(val id: Long) : MainIntent()
    data object DismissDeleteDialog : MainIntent()
}
