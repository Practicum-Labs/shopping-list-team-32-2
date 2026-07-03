package com.practicum.list.core.data

import kotlinx.coroutines.flow.Flow

interface SessionEvents {
    val sessionExpired: Flow<Unit>
    fun notifySessionExpired()
}