package com.practicum.list.core.data.network

import com.practicum.list.core.data.network.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response<out Any>
}