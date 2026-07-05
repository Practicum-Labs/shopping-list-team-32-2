package com.practicum.list.feature.auth.presentation.recovery

import com.practicum.list.core.mvi.MviState
import com.practicum.list.feature.auth.domain.validation.AuthValidation

data class ResetPasswordState(
    val email: String = "",
    val emailError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isEmailSent: Boolean = false,
) : MviState {
    val isSubmitEnabled: Boolean
        get() = AuthValidation.isRecoveryFormValid(email) && !isLoading && !isEmailSent
}
