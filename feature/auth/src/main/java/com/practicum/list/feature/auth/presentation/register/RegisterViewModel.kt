package com.practicum.list.feature.auth.presentation.register

import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.feature.auth.domain.models.RegisterResult
import com.practicum.list.feature.auth.domain.usecase.RegisterUserAuthUseCase
import com.practicum.list.feature.auth.domain.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserAuthUseCase: RegisterUserAuthUseCase,
) : MviViewModel<RegisterIntent, RegisterState, RegisterEffect>(RegisterState()) {

    override fun reduce(intent: RegisterIntent, current: RegisterState): RegisterState = when (intent) {
        is RegisterIntent.EmailChanged -> current.copy(
            email = intent.email,
            emailError = AuthValidation.emailFieldError(intent.email),
            generalError = null,
        )

        is RegisterIntent.PasswordChanged -> current.copy(
            password = intent.password,
            passwordError = AuthValidation.passwordFieldError(intent.password),
            confirmPasswordError = AuthValidation.confirmPasswordError(
                intent.password,
                current.confirmPassword,
            ),
            passwordStrengthLevel = AuthValidation.passwordStrengthLevel(intent.password),
            passwordRequirements = AuthValidation.passwordRequirements(intent.password),
            generalError = null,
        )

        is RegisterIntent.ConfirmPasswordChanged -> current.copy(
            confirmPassword = intent.confirmPassword,
            confirmPasswordError = AuthValidation.confirmPasswordError(
                current.password,
                intent.confirmPassword,
            ),
            generalError = null,
        )

        RegisterIntent.SubmitClicked -> current.copy(isLoading = true, generalError = null)

        RegisterIntent.BackClicked -> current
    }

    override suspend fun handleIntent(intent: RegisterIntent) {
        when (intent) {
            RegisterIntent.BackClicked -> emitEffect(RegisterEffect.NavigateBack)
            RegisterIntent.SubmitClicked -> submitRegister()
            else -> Unit
        }
    }

    private suspend fun submitRegister() {
        val email = state.value.email.trim()
        val password = state.value.password
        val confirmPassword = state.value.confirmPassword

        if (!AuthValidation.isRegisterFormValid(email, password, confirmPassword)) {
            updateState { it.copy(isLoading = false) }
            return
        }

        when (registerUserAuthUseCase(email, password)) {
            is RegisterResult.Success -> {
                updateState { it.copy(isLoading = false) }
                emitEffect(RegisterEffect.NavigateToMain)
            }

            RegisterResult.AlreadyExists -> updateState {
                it.copy(isLoading = false, emailError = ERROR_ALREADY_EXISTS)
            }

            RegisterResult.NoInternet -> finishWithError(ERROR_NO_INTERNET)
            RegisterResult.ServerError -> finishWithError(ERROR_SERVER)
            RegisterResult.WeakPassword -> updateState {
                it.copy(isLoading = false, passwordError = AuthValidation.passwordFieldError(password))
            }

            RegisterResult.IncorrectEmail -> updateState {
                it.copy(isLoading = false, emailError = AuthValidation.emailFieldError(email))
            }

            RegisterResult.Error -> finishWithError(ERROR_UNKNOWN)
        }
    }

    private suspend fun finishWithError(message: String) {
        updateState { it.copy(isLoading = false, generalError = message) }
        emitEffect(RegisterEffect.ShowError(message))
    }

    private companion object {
        const val ERROR_ALREADY_EXISTS = "Аккаунт с таким email уже зарегистрирован"
        const val ERROR_NO_INTERNET = "Нет соединения. Попробуйте ещё раз"
        const val ERROR_SERVER = "Ошибка сервера. Попробуйте позже"
        const val ERROR_UNKNOWN = "Не удалось зарегистрироваться. Попробуйте ещё раз"
    }
}
