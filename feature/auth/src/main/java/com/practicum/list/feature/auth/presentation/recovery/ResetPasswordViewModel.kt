package com.practicum.list.feature.auth.presentation.recovery

import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.feature.auth.domain.models.RecoverResult
import com.practicum.list.feature.auth.domain.usecase.RecoverPasswordAuthUseCase
import com.practicum.list.feature.auth.domain.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val recoverPasswordAuthUseCase: RecoverPasswordAuthUseCase,
) : MviViewModel<ResetPasswordIntent, ResetPasswordState, ResetPasswordEffect>(ResetPasswordState()) {

    override fun reduce(intent: ResetPasswordIntent, current: ResetPasswordState): ResetPasswordState =
        when (intent) {
            is ResetPasswordIntent.EmailChanged -> current.copy(
                email = intent.email,
                emailError = AuthValidation.emailFieldError(intent.email),
                generalError = null,
            )

            ResetPasswordIntent.SubmitClicked,
            ResetPasswordIntent.RetryClicked,
            -> current.copy(isLoading = true, generalError = null)

            ResetPasswordIntent.BackClicked,
            ResetPasswordIntent.ReturnToLoginClicked,
            -> current
        }

    override suspend fun handleIntent(intent: ResetPasswordIntent) {
        when (intent) {
            ResetPasswordIntent.BackClicked,
            ResetPasswordIntent.ReturnToLoginClicked,
            -> emitEffect(ResetPasswordEffect.NavigateBack)

            ResetPasswordIntent.SubmitClicked,
            ResetPasswordIntent.RetryClicked,
            -> submitRecovery()

            else -> Unit
        }
    }

    private suspend fun submitRecovery() {
        val email = state.value.email.trim()

        if (!AuthValidation.isRecoveryFormValid(email)) {
            updateState { it.copy(isLoading = false) }
            return
        }

        when (val result = recoverPasswordAuthUseCase(email)) {
            RecoverResult.Success -> updateState {
                it.copy(isLoading = false, isEmailSent = true, generalError = null)
            }

            is RecoverResult.NoInternet -> finishWithError(result.text)
            is RecoverResult.ServerError -> finishWithError(result.text)
            is RecoverResult.Error -> finishWithError(result.text)
        }
    }

    private suspend fun finishWithError(message: String) {
        updateState { it.copy(isLoading = false, generalError = message) }
        emitEffect(ResetPasswordEffect.ShowError(message))
    }
}
