package com.practicum.list.feature.auth.presentation.register

import com.practicum.list.core.mvi.MviState
import com.practicum.list.feature.auth.domain.validation.AuthValidation
import com.practicum.list.feature.auth.domain.validation.PasswordRequirements

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val passwordStrengthLevel: Int = 0,
    val passwordRequirements: PasswordRequirements = PasswordRequirements(
        hasMinLength = false,
        hasDigit = false,
        hasUppercase = false,
    ),
) : MviState {
    val isSubmitEnabled: Boolean
        get() = AuthValidation.isRegisterFormValid(email, password, confirmPassword) && !isLoading
}
