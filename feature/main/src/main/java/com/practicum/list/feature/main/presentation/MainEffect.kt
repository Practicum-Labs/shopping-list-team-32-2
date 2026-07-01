package com.practicum.list.feature.main.presentation

import com.practicum.list.core.mvi.MviEffect

sealed class MainEffect : MviEffect {
    data class NavigateToList(val id: Long) : MainEffect()
    data class ShowDeleteConfirmation(val id: Long, val name: String) : MainEffect()
    data class ShowRenameDialog(val id: Long, val currentName: String) : MainEffect()
    data class ShowCategoryPicker(val id: Long) : MainEffect()
    data class ShowError(val message: String) : MainEffect()
}
