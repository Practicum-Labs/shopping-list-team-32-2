package com.practicum.list.feature.auth.presentation.login

import com.practicum.list.core.mvi.MviEffect

sealed class LoginEffect : MviEffect {
    data object NavigateToMain : LoginEffect()
    data object NavigateToRegister : LoginEffect()
    data object NavigateToResetPassword : LoginEffect()
    data class ShowError(val message: String) : LoginEffect()
}
