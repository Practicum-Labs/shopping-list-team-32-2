package com.practicum.list.feature.auth.presentation.recovery

import com.practicum.list.core.common.utils.isEmailValid
import com.practicum.list.core.mvi.MviState

data class ResetPasswordState(
    val email: String = "",
    val emailError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isEmailSent: Boolean = false,
) : MviState {
    val isSubmitEnabled: Boolean
        get() = email.isEmailValid() && !isLoading && !isEmailSent
}
