package com.practicum.list.feature.list.presentation

import com.practicum.list.core.mvi.MviEffect

sealed class ListEffect : MviEffect{
    data object NavigateToMain: ListEffect()
    data class ShowError(val message: String): ListEffect()
}