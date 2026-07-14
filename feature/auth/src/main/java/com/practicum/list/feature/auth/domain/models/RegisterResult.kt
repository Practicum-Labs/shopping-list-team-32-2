package com.practicum.list.feature.auth.domain.models

sealed interface RegisterResult {
    data class Success(val session: UserSession) : RegisterResult

    data class NoInternet(val message: String) : RegisterResult

    data class ServerError(val message: String) : RegisterResult

    data class WeakPassword(val text: String) : RegisterResult

    data class IncorrectEmail(val text: String) : RegisterResult

    data class AlreadyExists(val text: String) : RegisterResult

    data class Error(val message: String) : RegisterResult
}
