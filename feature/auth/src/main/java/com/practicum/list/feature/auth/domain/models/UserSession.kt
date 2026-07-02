package com.practicum.list.feature.auth.domain.models

data class UserSession(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String,
)
