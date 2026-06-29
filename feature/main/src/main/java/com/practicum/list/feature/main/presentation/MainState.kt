package com.practicum.list.feature.main.presentation

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.mvi.MviState

data class MainState(
    val lists: List<ShoppingList> = emptyList(),
    val isLoading: Boolean = true,
) : MviState {
    val isEmpty: Boolean get() = lists.isEmpty()
}
