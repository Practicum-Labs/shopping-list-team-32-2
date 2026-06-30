package com.practicum.list.feature.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
    val token: String = "",
    val userId: String = ""
)
