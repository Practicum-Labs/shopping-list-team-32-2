package com.practicum.list.core.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenRequest(
    @field:Json(name = "refresh_token")
    val refreshToken: String
)