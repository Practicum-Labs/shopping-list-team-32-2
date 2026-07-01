package com.practicum.list.core.data.network.dto

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response<out Any>
}