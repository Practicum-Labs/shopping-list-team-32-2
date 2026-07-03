package com.practicum.list.feature.auth.domain.models

sealed interface RecoverResult {
    data object Success : RecoverResult

    data object NoInternet : RecoverResult

    data object ServerError : RecoverResult

    data class Error(val text: String) : RecoverResult
}