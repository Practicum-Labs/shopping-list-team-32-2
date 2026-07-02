package com.practicum.list.feature.auth.domain.models

sealed interface RegisterResult {
    data class Success(val session: UserSession) : RegisterResult

    data object NoInternet : RegisterResult

    data object ServerError : RegisterResult

    data object WeakPassword : RegisterResult

    data object IncorrectEmail : RegisterResult

    data object AlreadyExists : RegisterResult

    data object Error: RegisterResult
}
