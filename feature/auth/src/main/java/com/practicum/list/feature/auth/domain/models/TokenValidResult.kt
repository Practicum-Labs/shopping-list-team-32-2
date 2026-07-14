package com.practicum.list.feature.auth.domain.models

sealed interface TokenValidResult {
    data class Success(val isValid: Boolean) : TokenValidResult

    data class NoInternet(val text: String) : TokenValidResult

    data class ServerError(val text: String) : TokenValidResult

    data class Error(val text: String) : TokenValidResult
}
