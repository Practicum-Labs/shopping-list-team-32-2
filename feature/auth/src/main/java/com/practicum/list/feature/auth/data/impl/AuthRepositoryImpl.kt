package com.practicum.list.feature.auth.data.impl

import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.common.utils.isPasswordWeak
import com.practicum.list.core.data.network.NetworkClient
import com.practicum.list.core.data.network.codes.BAD_REQUEST_ERROR
import com.practicum.list.core.data.network.codes.CONFLICT_ERROR
import com.practicum.list.core.data.network.codes.DEFAULT_ERROR
import com.practicum.list.core.data.network.codes.OK
import com.practicum.list.core.data.network.codes.SERVER_ERROR
import com.practicum.list.core.data.network.codes.UNAUTHORIZED_ERROR
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
            DEFAULT_ERROR -> AuthResult.NoInternet(NO_INTERNET_ERROR)
            BAD_REQUEST_ERROR -> {
                if (password.isPasswordWeak(MIN_PASSWORD_LENGTH)) {
                    AuthResult.WeakPassword(WEAK_PASSWORD)
                } else {
                    AuthResult.IncorrectEmail(INCORRECT_EMAIL_ERROR)
                }
            }
            OK -> {
                val data = response.data as? UserAuthResponse
                if (data == null) {
                    AuthResult.Error(UNKNOWN_ERROR_MESSAGE)
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
            UNAUTHORIZED_ERROR -> AuthResult.IncorrectCredentials(INCORRECT_CREDENTIALS)
            SERVER_ERROR -> AuthResult.ServerError(SERVER_ERROR_MESSAGE)
            else -> AuthResult.Error(UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun refreshToken(): RefreshResult {
        val refreshToken = userSession.getRefreshToken() ?:
        return RefreshResult.Error(EMPTY_TOKEN_ERROR)
        val userId = userSession.getUserId()
        val response = networkClient.doRequest(RefreshTokenRequest(refreshToken))
        return when (response.resultCode) {
            DEFAULT_ERROR -> RefreshResult.NoInternet(NO_INTERNET_ERROR)
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
            SERVER_ERROR -> RefreshResult.ServerError(SERVER_ERROR_MESSAGE)
            else -> RefreshResult.Error(UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun checkTokenIsValid(token: String): TokenValidResult {
        val response = networkClient.doRequest(CheckTokenRequest(token))
        return when (response.resultCode) {
            DEFAULT_ERROR -> TokenValidResult.NoInternet(NO_INTERNET_ERROR)
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
            SERVER_ERROR -> TokenValidResult.ServerError(SERVER_ERROR_MESSAGE)
            else -> TokenValidResult.Error(UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun recoverPassword(email: String): RecoverResult {
        val response = networkClient.doRequest(RecoverPasswordRequest(email))
        return when (response.resultCode) {
            DEFAULT_ERROR -> RecoverResult.NoInternet(NO_INTERNET_ERROR)
            OK -> RecoverResult.Success
            SERVER_ERROR -> RecoverResult.ServerError(SERVER_ERROR_MESSAGE)
            else -> RecoverResult.Error(UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun registerUser(email: String, password: String): RegisterResult {
        val response = networkClient.doRequest(RegisterRequest(email, password))
        return when (response.resultCode) {
            DEFAULT_ERROR -> RegisterResult.NoInternet(NO_INTERNET_ERROR)
            BAD_REQUEST_ERROR ->
                if (password.isPasswordWeak(MIN_PASSWORD_LENGTH)) {
                    RegisterResult.WeakPassword(WEAK_PASSWORD)
                } else {
                    RegisterResult.IncorrectEmail(INCORRECT_EMAIL_ERROR)
                }
            CONFLICT_ERROR -> RegisterResult.AlreadyExists(ALREADY_EXISTS_ERROR)
            OK -> {
                val data = response.data as? RegisterResponse
                if (data == null) {
                    RegisterResult.Error(UNKNOWN_ERROR_MESSAGE)
                } else {
                    val session = data.toDomain()
                    userSession.saveSession(
                        session.userId.toLong(),
                        session.accessToken,
                        session.refreshToken,
                    )
                    RegisterResult.Success(session)
                }
            }
            SERVER_ERROR -> RegisterResult.ServerError(SERVER_ERROR_MESSAGE)
            else -> RegisterResult.Error(UNKNOWN_ERROR_MESSAGE)
        }
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 7
        const val UNKNOWN_ERROR_MESSAGE = "Неизвестная ошибка"
        const val EMPTY_RESPONSE_MESSAGE = "С сервера пришел пустой ответ"

        const val NO_INTERNET_ERROR = "Нет интернета"

        const val INCORRECT_EMAIL_ERROR = "Некорректный email"

        const val SERVER_ERROR_MESSAGE = "Сервер недоступен"

        const val ALREADY_EXISTS_ERROR = "Пользователь уже существует"

        const val WEAK_PASSWORD = "Пароль не соответствует требованиям безопасности, вас взломают!"

        const val EMPTY_TOKEN_ERROR = "Пустой рефреш токен"

        const val INCORRECT_CREDENTIALS = "Не совпадают email и пароль"
    }
}
