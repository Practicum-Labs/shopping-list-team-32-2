package com.practicum.list.feature.auth.presentation.recovery

import com.practicum.list.core.mvi.MviEffect

sealed class ResetPasswordEffect : MviEffect {
    data object NavigateBack : ResetPasswordEffect()
    data class ShowError(val message: String) : ResetPasswordEffect()
}
