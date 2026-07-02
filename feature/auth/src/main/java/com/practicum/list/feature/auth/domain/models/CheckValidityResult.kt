package com.practicum.list.feature.auth.domain.models

sealed interface TokenValidResult {
    data class Success(val isValid: Boolean) : TokenValidResult

    data object NoInternet : TokenValidResult

    data object ServerError : TokenValidResult

    data class Error(val text: String): TokenValidResult
}