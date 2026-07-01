package com.practicum.list.core.data.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAuthRequest(val email: String, val password: String)