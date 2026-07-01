package com.practicum.list.core.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckResponse(
    @field:Json(name = "is_valid")
    val isValid: Boolean,
)