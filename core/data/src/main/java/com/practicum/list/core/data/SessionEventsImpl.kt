package com.practicum.list.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionEventsImpl @Inject constructor() : SessionEvents {
    private val _sessionExpired = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    override val sessionExpired: Flow<Unit> = _sessionExpired.asSharedFlow()
    override fun notifySessionExpired() {
        _sessionExpired.tryEmit(Unit)
    }
}