package com.practicum.list.feature.auth.data

import com.practicum.list.core.data.network.dto.RegisterResponse
import com.practicum.list.feature.auth.domain.models.UserSession


fun RegisterResponse.toDomain(): UserSession = UserSession(
    userId = userId.toInt(),
    accessToken = accessToken,
    refreshToken = refreshToken,
)