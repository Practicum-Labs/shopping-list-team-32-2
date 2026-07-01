package com.practicum.list.core.data.network.dto

data class Response<T>(
    val data: T?,
    val resultCode: Int = 0,
)