package com.practicum.list.feature.auth.presentation.register

import com.practicum.list.core.mvi.MviEffect

sealed class RegisterEffect : MviEffect {
    data object NavigateToMain : RegisterEffect()
    data object NavigateBack : RegisterEffect()
    data class ShowError(val message: String) : RegisterEffect()
}
