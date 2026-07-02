package com.practicum.list.feature.auth.domain

import com.practicum.list.feature.auth.domain.models.RegisterResult

interface AuthRepository {

    suspend fun registerUser(email: String, password: String): RegisterResult
    suspend fun loginUser(email: String, password: String)
    suspend fun refreshToken()
    suspend fun checkTokenIsValid(token: String)
    suspend fun recoverPassword(email: String)
}