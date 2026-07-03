package com.practicum.list.feature.auth.domain

import com.practicum.list.feature.auth.domain.models.AuthResult
import com.practicum.list.feature.auth.domain.models.RecoverResult
import com.practicum.list.feature.auth.domain.models.RefreshResult
import com.practicum.list.feature.auth.domain.models.RegisterResult
import com.practicum.list.feature.auth.domain.models.TokenValidResult

interface AuthRepository {

    suspend fun registerUser(email: String, password: String): RegisterResult
    suspend fun loginUser(email: String, password: String): AuthResult
    suspend fun refreshToken(): RefreshResult
    suspend fun checkTokenIsValid(token: String): TokenValidResult
    suspend fun recoverPassword(email: String): RecoverResult
}