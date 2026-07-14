package com.practicum.list.feature.main.domain.usecase

import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.SessionEvents
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val userSession: UserSession,
    private val sessionEvents: SessionEvents,
) {
    suspend operator fun invoke() {
        userSession.clearSession()
        sessionEvents.notifySessionExpired()
    }
}
