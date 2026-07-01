package com.practicum.list.core.data.network.api

import com.practicum.list.core.data.network.dto.CheckResponse
import com.practicum.list.core.data.network.dto.RefreshTokenRequest
import com.practicum.list.core.data.network.dto.RefreshTokenResponse
import com.practicum.list.core.data.network.dto.RegisterRequest
import com.practicum.list.core.data.network.dto.RegisterResponse
import com.practicum.list.core.data.network.dto.Response
import com.practicum.list.core.data.network.dto.UserAuthRequest
import com.practicum.list.core.data.network.dto.UserAuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/registration")
    suspend fun registerUser(
        @Body user: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    suspend fun loginUser(
        @Body user: UserAuthRequest
    ): UserAuthResponse

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): RefreshTokenResponse

    @GET("auth/check")
    suspend fun checkTokenIsValid(
        @Header("Authorization") token: String
    ): CheckResponse

    @POST("auth/recovery")
    suspend fun recoverPassword(
        @Header("email") email: String
    ): Response<Unit>
}