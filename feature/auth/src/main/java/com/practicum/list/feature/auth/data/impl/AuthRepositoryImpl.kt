package com.practicum.list.feature.auth.data.impl

import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.common.utils.isPasswordWeak
import com.practicum.list.core.data.network.NetworkClient
import com.practicum.list.core.data.network.codes.BAD_REQUEST_ERROR
import com.practicum.list.core.data.network.codes.CONFLICT_ERROR
import com.practicum.list.core.data.network.codes.DEFAULT_ERROR
import com.practicum.list.core.data.network.codes.OK
import com.practicum.list.core.data.network.codes.SERVER_ERROR
import com.practicum.list.core.data.network.dto.CheckResponse
import com.practicum.list.core.data.network.dto.CheckTokenRequest
import com.practicum.list.core.data.network.dto.RecoverPasswordRequest
import com.practicum.list.core.data.network.dto.RefreshTokenRequest
import com.practicum.list.core.data.network.dto.RefreshTokenResponse
import com.practicum.list.core.data.network.dto.RegisterRequest
import com.practicum.list.core.data.network.dto.RegisterResponse
import com.practicum.list.core.data.network.dto.UserAuthRequest
import com.practicum.list.core.data.network.dto.UserAuthResponse
import com.practicum.list.feature.auth.data.toDomain
import com.practicum.list.feature.auth.domain.AuthRepository
import com.practicum.list.feature.auth.domain.models.AuthResult
import com.practicum.list.feature.auth.domain.models.RecoverResult
import com.practicum.list.feature.auth.domain.models.RefreshResult
import com.practicum.list.feature.auth.domain.models.RegisterResult
import com.practicum.list.feature.auth.domain.models.TokenValidResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val userSession: UserSession,
) : AuthRepository {

    override suspend fun loginUser(email: String, password: String): AuthResult {
        val response = networkClient.doRequest(UserAuthRequest(email, password))
        return when (response.resultCode) {
            DEFAULT_ERROR -> AuthResult.NoInternet
            BAD_REQUEST_ERROR -> {
                if (password.isPasswordWeak(MIN_PASSWORD_LENGTH)) {
                    AuthResult.WeakPassword
                } else {
                    AuthResult.IncorrectEmail
                }
            }
            OK -> {
                val data = response.data as? UserAuthResponse
                if (data == null) {
                    AuthResult.Error
                } else {
                    val session = data.toDomain()
                    userSession.saveSession(
                        session.userId.toLong(),
                        session.accessToken,
                        session.refreshToken,
                    )
                    AuthResult.Success(session)
                }
            }
            SERVER_ERROR -> AuthResult.ServerError
            else -> AuthResult.Error
        }
    }

    override suspend fun refreshToken(): RefreshResult {
        val refreshToken = userSession.getRefreshToken() ?: return RefreshResult.Error("Пустой рефреш токен")
        val userId = userSession.getUserId()
        val response = networkClient.doRequest(RefreshTokenRequest(refreshToken))
        return when (response.resultCode) {
            DEFAULT_ERROR -> RefreshResult.NoInternet
            OK -> {
                val data = response.data as? RefreshTokenResponse
                if (data == null) {
                    RefreshResult.Error(EMPTY_RESPONSE_MESSAGE)
                } else {
                    val session = data.toDomain()
                    userSession.saveSession(
                        userId,
                        session.accessToken,
                        session.refreshToken,
                    )
                    RefreshResult.Success(session)
                }
            }
            SERVER_ERROR -> RefreshResult.ServerError
            else -> RefreshResult.Error(UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun checkTokenIsValid(token: String): TokenValidResult {
        val response = networkClient.doRequest(CheckTokenRequest(token))
        return when (response.resultCode) {
            DEFAULT_ERROR -> TokenValidResult.NoInternet
            OK -> {
                val data = response.data as? CheckResponse
                if (data == null) {
                    TokenValidResult.Error(EMPTY_RESPONSE_MESSAGE)
                } else {
                    val validResponse = data.toDomain()
                    if (!validResponse.isValid) {
                        userSession.clearSession()
                    }
                    TokenValidResult.Success(validResponse.isValid)
                }
            }
            SERVER_ERROR -> TokenValidResult.ServerError
            else -> TokenValidResult.Error(UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun recoverPassword(email: String): RecoverResult {
        val response = networkClient.doRequest(RecoverPasswordRequest(email))
        return when (response.resultCode) {
            DEFAULT_ERROR -> RecoverResult.NoInternet
            OK -> RecoverResult.Success
            SERVER_ERROR -> RecoverResult.ServerError
            else -> RecoverResult.Error(UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun registerUser(email: String, password: String): RegisterResult {
        val userId = userSession.getUserId()
        val response = networkClient.doRequest(RegisterRequest(email, password))
        return when (response.resultCode) {
            DEFAULT_ERROR -> RegisterResult.NoInternet
            BAD_REQUEST_ERROR ->
                if (password.isPasswordWeak(MIN_PASSWORD_LENGTH)) {
                    RegisterResult.WeakPassword
                } else {
                    RegisterResult.IncorrectEmail
                }
            CONFLICT_ERROR -> RegisterResult.AlreadyExists
            OK -> {
                val data = response.data as? RegisterResponse
                if (data == null) {
                    RegisterResult.Error
                } else {
                    val session = data.toDomain()
                    userSession.saveSession(
                        userId,
                        session.accessToken,
                        session.refreshToken,
                    )
                    RegisterResult.Success(session)
                }
            }
            SERVER_ERROR -> RegisterResult.ServerError
            else -> RegisterResult.Error
        }
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 7
        const val UNKNOWN_ERROR_MESSAGE = "Неизвестная ошибка"
        const val EMPTY_RESPONSE_MESSAGE = "С сервера пришел пустой ответ"
    }
}
