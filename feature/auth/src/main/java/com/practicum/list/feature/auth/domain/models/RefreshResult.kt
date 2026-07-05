package com.practicum.list.feature.auth.domain.models

sealed interface RefreshResult {
    data class Success(val session: UserSession) : RefreshResult

    data class NoInternet(val text: String) : RefreshResult

    data class ServerError(val text: String) : RefreshResult

    data class Error(val text: String) : RefreshResult
}