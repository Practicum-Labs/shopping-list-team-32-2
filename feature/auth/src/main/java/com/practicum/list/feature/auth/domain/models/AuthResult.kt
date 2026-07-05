package com.practicum.list.feature.auth.domain.models

sealed interface AuthResult {
    data class Success(val session: UserSession) : AuthResult

    data class NoInternet(val text: String) : AuthResult

    data class ServerError(val text: String) : AuthResult

    data class WeakPassword(val text: String) : AuthResult

    data class IncorrectEmail(val text: String) : AuthResult

    data class IncorrectCredentials(val text: String) : AuthResult

    data class Error(val text: String) : AuthResult
}