package com.practicum.list.feature.auth.domain.models

sealed interface RefreshResult {
    data class Success(val session: UserSession) : RefreshResult

    data object NoInternet : RefreshResult

    data object ServerError : RefreshResult

    data object Error: RefreshResult
}