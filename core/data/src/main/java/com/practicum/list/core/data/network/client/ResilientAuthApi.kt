package com.practicum.list.core.data.network.client

import android.util.Log
import com.practicum.list.core.data.network.ApiHostSelector
import com.practicum.list.core.data.network.api.AuthApi
import com.practicum.list.core.data.network.dto.CheckResponse
import com.practicum.list.core.data.network.dto.RefreshTokenRequest
import com.practicum.list.core.data.network.dto.RefreshTokenResponse
import com.practicum.list.core.data.network.dto.RegisterRequest
import com.practicum.list.core.data.network.dto.RegisterResponse
import com.practicum.list.core.data.network.dto.UserAuthRequest
import com.practicum.list.core.data.network.dto.UserAuthResponse
import retrofit2.HttpException
import java.io.IOException

class ResilientAuthApi(
    private val primary: AuthApi,
    private val fallback: AuthApi,
    private val hostSelector: ApiHostSelector,
) {
    suspend fun registerUser(user: RegisterRequest): RegisterResponse =
        executeWithFallback { registerUser(user) }

    suspend fun loginUser(user: UserAuthRequest): UserAuthResponse =
        executeWithFallback { loginUser(user) }

    suspend fun refreshToken(request: RefreshTokenRequest): RefreshTokenResponse =
        executeWithFallback { refreshToken(request) }

    suspend fun checkTokenIsValid(token: String): CheckResponse =
        executeWithFallback { checkTokenIsValid(token) }

    suspend fun recoverPassword(email: String) =
        executeWithFallback { recoverPassword(email) }

    private suspend inline fun <T> executeWithFallback(
        crossinline call: suspend AuthApi.() -> T,
    ): T {
        if (hostSelector.shouldUseFallbackFirst()) {
            return executeOnFallback(call)
        }

        return try {
            primary.call().also { hostSelector.markPrimaryAvailable() }
        } catch (primaryError: Exception) {
            if (!shouldUseFallback(primaryError)) throw primaryError
            Log.w(TAG, "Primary API unavailable, retrying via fallback proxy", primaryError)
            hostSelector.markPrimaryUnavailable()
            executeOnFallback(call)
        }
    }

    private suspend inline fun <T> executeOnFallback(
        crossinline call: suspend AuthApi.() -> T,
    ): T {
        return try {
            fallback.call()
        } catch (fallbackError: Exception) {
            Log.e(TAG, "Fallback API request failed", fallbackError)
            throw fallbackError
        }
    }

    private fun shouldUseFallback(error: Exception): Boolean = when (error) {
        is IOException -> true
        is HttpException -> error.code() in FALLBACK_HTTP_CODES
        else -> false
    }

    private companion object {
        const val TAG = "ResilientAuthApi"
        val FALLBACK_HTTP_CODES = setOf(502, 503, 504)
    }
}
