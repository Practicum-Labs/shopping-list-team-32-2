package com.practicum.list.feature.auth.presentation.login

import com.practicum.list.core.mvi.MviIntent

sealed class LoginIntent : MviIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data object SubmitClicked : LoginIntent()
    data object RegisterClicked : LoginIntent()
    data object ResetPasswordClicked : LoginIntent()
    data object RetryClicked : LoginIntent()
    data class SubmitEmailClicked(val email: String) : LoginIntent()
    data class SubmitPasswordClicked(val password: String) : LoginIntent()
}
