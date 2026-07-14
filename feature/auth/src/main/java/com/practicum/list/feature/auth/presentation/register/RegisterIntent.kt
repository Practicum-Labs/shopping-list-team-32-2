package com.practicum.list.feature.auth.presentation.register

import com.practicum.list.core.mvi.MviIntent

sealed class RegisterIntent : MviIntent {
    data class EmailChanged(val email: String) : RegisterIntent()
    data class PasswordChanged(val password: String) : RegisterIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterIntent()
    data class SubmitEmailClicked(val email: String) : RegisterIntent()
    data object SubmitClicked : RegisterIntent()
    data object BackClicked : RegisterIntent()
}
