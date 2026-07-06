package com.practicum.list.feature.auth.presentation.recovery

import com.practicum.list.core.mvi.MviIntent

sealed class ResetPasswordIntent : MviIntent {
    data class EmailChanged(val email: String) : ResetPasswordIntent()
    data object SubmitClicked : ResetPasswordIntent()
    data object BackClicked : ResetPasswordIntent()
}
