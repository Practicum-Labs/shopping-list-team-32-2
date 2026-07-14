package com.practicum.list.feature.auth.domain.models

sealed interface RecoverResult {
    data object Success : RecoverResult

    data class NoInternet(val text: String) : RecoverResult

    data class ServerError(val text: String) : RecoverResult

    data class Error(val text: String) : RecoverResult
}