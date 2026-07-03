package com.practicum.list.core.data.network

import com.practicum.list.core.data.SessionEvents
import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.di.RefreshAuthApi
import com.practicum.list.core.data.network.api.AuthApi
import com.practicum.list.core.data.network.dto.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenAuthenticator @Inject constructor(
    private val userSession: UserSession,
    @RefreshAuthApi private val refreshAuthApi: AuthApi,
    private val sessionEvents: SessionEvents,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.priorResponse != null) return null // уже ретраили — стоп
        synchronized(this) {
            val refreshToken = runBlocking { userSession.getRefreshToken() }
                ?: return forceLogout()
            val tokens = runBlocking {
                try {
                    refreshAuthApi.refreshToken(RefreshTokenRequest(refreshToken))
                } catch (_: Exception) {
                    null
                }
            } ?: return forceLogout()
            val userId = runBlocking { userSession.getUserId() }
            runBlocking {
                userSession.saveSession(userId, tokens.accessToken, tokens.refreshToken)
            }
            return response.request.newBuilder()
                .header("Authorization", "Bearer ${tokens.accessToken}")
                .build()
        }
    }
    private fun forceLogout(): Request? {
        runBlocking { userSession.clearSession() }
        sessionEvents.notifySessionExpired()
        return null
    }
}