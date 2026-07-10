package com.practicum.list.core.data.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHostSelector @Inject constructor() {
    @Volatile
    private var useFallback: Boolean = false

    fun shouldUseFallbackFirst(): Boolean = useFallback

    fun markPrimaryUnavailable() {
        useFallback = true
    }

    fun markPrimaryAvailable() {
        useFallback = false
    }
}
