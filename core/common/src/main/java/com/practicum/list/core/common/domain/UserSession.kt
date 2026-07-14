package com.practicum.list.core.common.domain

import kotlinx.coroutines.flow.Flow

interface UserSession {
    val userId: Flow<Long>

    suspend fun getUserId(): Long

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun saveSession(userId: Long, accessToken: String, refreshToken: String)

    suspend fun clearSession()
}
