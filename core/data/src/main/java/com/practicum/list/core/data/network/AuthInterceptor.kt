package com.practicum.list.core.data.network

import android.util.Log
import com.practicum.list.core.common.domain.UserSession
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val userSession: UserSession
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { userSession.getAccessToken() }

        val requestBuilder = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
        } else {
            Log.e("AuthInterceptor", "Token is null")
            originalRequest.newBuilder()
        }

        val request = requestBuilder
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}
