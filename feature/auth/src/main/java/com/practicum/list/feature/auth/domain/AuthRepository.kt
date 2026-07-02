package com.practicum.list.feature.auth.domain

interface AuthRepository {

    suspend fun registerUser(email: String, password: String)
    suspend fun loginUser(email: String, password: String)
    suspend fun refreshToken()
    suspend fun checkTokenIsValid(token: String)
    suspend fun recoverPassword(email: String)
}