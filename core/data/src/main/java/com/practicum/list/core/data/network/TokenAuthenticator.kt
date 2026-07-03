package com.practicum.list.core.data.network

import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.SessionEvents
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
        if (response.priorResponse != null) return null

        val accessToken = synchronized(this) { resolveAccessToken(response) }
        return when (accessToken) {
            null -> forceLogout()
            else -> authorizedRequest(response, accessToken)
        }
    }

    private fun resolveAccessToken(response: Response): String? {
        val currentAccessToken = runBlocking { userSession.getAccessToken() }
        val failedAccessToken = response.request.header(AUTHORIZATION_HEADER)
            ?.removePrefix(BEARER_PREFIX)
            ?.trim()
        if (currentAccessToken != null && currentAccessToken != failedAccessToken) {
            return currentAccessToken
        }
        return refreshAccessToken()
    }

    private fun refreshAccessToken(): String? {
        val refreshToken = runBlocking { userSession.getRefreshToken() }
        val tokens = refreshToken?.let { token ->
            runBlocking {
                try {
                    refreshAuthApi.refreshToken(RefreshTokenRequest(token))
                } catch (_: Exception) {
                    null
                }
            }
        }

        return tokens?.let { response ->
            val userId = runBlocking { userSession.getUserId() }
            runBlocking {
                userSession.saveSession(userId, response.accessToken, response.refreshToken)
            }
            response.accessToken
        }
    }

    private fun authorizedRequest(response: Response, accessToken: String): Request =
        response.request.newBuilder()
            .header(AUTHORIZATION_HEADER, "$BEARER_PREFIX$accessToken")
            .build()

    private fun forceLogout(): Request? {
        runBlocking { userSession.clearSession() }
        sessionEvents.notifySessionExpired()
        return null
    }

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }
}
