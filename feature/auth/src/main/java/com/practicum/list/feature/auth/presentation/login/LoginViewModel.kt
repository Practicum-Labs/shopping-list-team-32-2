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

        when (loginUserAuthUseCase(email, password)) {
            is AuthResult.Success -> {
                updateState { it.copy(isLoading = false) }
                emitEffect(LoginEffect.NavigateToMain)
            }

            AuthResult.NoInternet -> finishWithGeneralError(ERROR_NO_INTERNET)
            AuthResult.ServerError -> finishWithGeneralError(ERROR_SERVER)
            AuthResult.IncorrectCredentials -> finishWithGeneralError(ERROR_INVALID_CREDENTIALS)
            AuthResult.WeakPassword -> updateState {
                it.copy(isLoading = false, passwordError = AuthValidation.passwordFieldError(password))
            }

            AuthResult.IncorrectEmail -> updateState {
                it.copy(isLoading = false, emailError = AuthValidation.emailFieldError(email))
            }

            AuthResult.Error -> finishWithGeneralError(ERROR_UNKNOWN)
        }
    }

    private suspend fun finishWithGeneralError(message: String) {
        updateState { it.copy(isLoading = false, generalError = message) }
        emitEffect(LoginEffect.ShowError(message))
    }

    private companion object {
        const val ERROR_INVALID_CREDENTIALS = "Неверный email или пароль"
        const val ERROR_NO_INTERNET = "Нет соединения. Проверьте интернет и попробуйте снова"
        const val ERROR_SERVER = "Ошибка сервера. Попробуйте позже"
        const val ERROR_UNKNOWN = "Не удалось войти. Попробуйте ещё раз"
    }
}
