package com.practicum.list.feature.auth.domain.models

sealed interface AuthResult {
    data class Success(val session: UserSession) : AuthResult

    data object NoInternet : AuthResult

    data object ServerError : AuthResult

    data object WeakPassword : AuthResult

    data object IncorrectEmail : AuthResult

    data object Error : AuthResult
}