package com.practicum.list.feature.auth.presentation.login

import com.practicum.list.core.mvi.MviState
import com.practicum.list.feature.auth.domain.validation.AuthValidation

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
) : MviState {
    val isSubmitEnabled: Boolean
        get() = AuthValidation.isLoginFormValid(email, password) && !isLoading
}
