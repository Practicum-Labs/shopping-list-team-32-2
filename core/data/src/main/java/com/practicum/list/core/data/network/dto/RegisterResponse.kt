package com.practicum.list.core.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    @field:Json(name = "user_id")
    val userId: Long,
    @field:Json(name = "access_token")
    val accessToken: String,
    @field:Json(name = "refresh_token")
    val refreshToken: String
)