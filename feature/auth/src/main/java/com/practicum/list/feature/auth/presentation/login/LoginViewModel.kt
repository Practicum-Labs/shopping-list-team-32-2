package com.practicum.list.feature.auth.presentation.login

import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.feature.auth.domain.models.AuthResult
import com.practicum.list.feature.auth.domain.usecase.LoginUserAuthUseCase
import com.practicum.list.feature.auth.domain.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserAuthUseCase: LoginUserAuthUseCase,
) : MviViewModel<LoginIntent, LoginState, LoginEffect>(LoginState()) {

    override fun reduce(intent: LoginIntent, current: LoginState): LoginState = when (intent) {
        is LoginIntent.EmailChanged -> current.copy(
            email = intent.email,
            emailError = AuthValidation.emailFieldError(intent.email),
            generalError = null,
        )

        is LoginIntent.PasswordChanged -> current.copy(
            password = intent.password,
            passwordError = AuthValidation.passwordFieldError(intent.password),
            generalError = null,
        )

        LoginIntent.SubmitClicked,
        LoginIntent.RetryClicked,
        -> current.copy(isLoading = true, generalError = null)

        LoginIntent.RegisterClicked,
        LoginIntent.ResetPasswordClicked,
        -> current
    }

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            LoginIntent.RegisterClicked -> emitEffect(LoginEffect.NavigateToRegister)
            LoginIntent.ResetPasswordClicked -> emitEffect(LoginEffect.NavigateToResetPassword)
            LoginIntent.SubmitClicked,
            LoginIntent.RetryClicked,
            -> submitLogin()

            else -> Unit
        }
    }

    private suspend fun submitLogin() {
        val email = state.value.email.trim()
        val password = state.value.password

        if (!AuthValidation.isLoginFormValid(email, password)) {
            updateState { it.copy(isLoading = false) }
            return
        }

        when (val result = loginUserAuthUseCase(email, password)) {
            is AuthResult.Success -> {
                updateState { it.copy(isLoading = false) }
                emitEffect(LoginEffect.NavigateToMain)
            }

            is AuthResult.NoInternet -> finishWithGeneralError(result.text)
            is AuthResult.ServerError -> finishWithGeneralError(result.text)
            is AuthResult.IncorrectCredentials -> finishWithGeneralError(result.text)
            is AuthResult.WeakPassword -> updateState {
                it.copy(isLoading = false, passwordError = AuthValidation.passwordFieldError(password))
            }

            is AuthResult.IncorrectEmail -> updateState {
                it.copy(isLoading = false, emailError = AuthValidation.emailFieldError(email))
            }

            is AuthResult.Error -> finishWithGeneralError(result.text)
        }
    }

    private suspend fun finishWithGeneralError(message: String) {
        updateState { it.copy(isLoading = false, generalError = message) }
        emitEffect(LoginEffect.ShowError(message))
    }
}
