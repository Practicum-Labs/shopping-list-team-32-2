package com.practicum.list.feature.main.presentation

import com.practicum.list.core.mvi.MviIntent

sealed class MainIntent : MviIntent {
    data object LoadLists : MainIntent()
    data class OpenList(val id: Long) : MainIntent()
    data class DeleteList(val id: Long) : MainIntent()
    data class ConfirmDeleteList(val id: Long) : MainIntent()
    data class RenameList(val id: Long) : MainIntent()
    data class ConfirmRenameList(val id: Long, val newName: String) : MainIntent()
    data class DuplicateList(val id: Long) : MainIntent()
    data object CreateListClicked : MainIntent()
    data class CreateListNameChanged(val name: String) : MainIntent()
    data object ConfirmCreateList : MainIntent()
    data object DismissCreateListDialog : MainIntent()
}
