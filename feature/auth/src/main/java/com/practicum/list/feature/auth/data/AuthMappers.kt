package com.practicum.list.feature.auth.data

import com.practicum.list.core.data.network.dto.CheckResponse
import com.practicum.list.core.data.network.dto.RefreshTokenResponse
import com.practicum.list.core.data.network.dto.RegisterResponse
import com.practicum.list.core.data.network.dto.UserAuthResponse
import com.practicum.list.feature.auth.domain.models.UserSession
import com.practicum.list.feature.auth.domain.models.ValidResult


fun RegisterResponse.toDomain(): UserSession = UserSession(
    userId = userId.toInt(),
    accessToken = accessToken,
    refreshToken = refreshToken,
)

fun UserAuthResponse.toDomain(): UserSession = UserSession(
    userId = userId.toInt(),
    accessToken = accessToken,
    refreshToken = refreshToken,
)

fun RefreshTokenResponse.toDomain(): UserSession = UserSession(
    userId = 0,
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun CheckResponse.toDomain(): ValidResult = ValidResult(
    isValid = isValid
)