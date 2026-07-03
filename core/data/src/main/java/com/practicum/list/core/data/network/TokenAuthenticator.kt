package com.practicum.list.core.data.network

import android.content.Context
import android.content.Intent
import com.practicum.list.core.common.domain.UserSession
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenAuthenticator @Inject constructor(
    val context: Context,
    val userSession: UserSession
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            val refreshToken = userSession.getRefreshToken() ?: return null

            // 1. Attempt to refresh the access token via a synchronous API call
            val newToken = userSession. //refreshAccessTokenSync(refreshToken)

            if (newToken != null) {
                // Success: Update new tokens in local storage
                userSession.saveSession(newToken.accessToken, ) saveTokens(newToken.accessToken, newToken.refreshToken)

                // Retry the original failed request with the new token
                return response.request.newBuilder()
                    .header("Authorization", "Bearer ${newToken.accessToken}")
                    .build()
            }

            // 2. Failure: Refresh token is also invalid. Force logout.
            userSession.clearSession()
            triggerLoginFlow()
            return null
        }
    }

    private fun triggerLoginFlow() {
        val intent = Intent("ACTION_FORCE_LOGOUT")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}