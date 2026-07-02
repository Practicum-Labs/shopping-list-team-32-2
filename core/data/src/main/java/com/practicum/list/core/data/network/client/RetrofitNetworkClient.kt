package com.practicum.list.core.data.network.client

import android.util.Log
import com.practicum.list.core.common.domain.AuthError
import com.practicum.list.core.common.utils.NetworkConnectionChecker
import com.practicum.list.core.common.utils.isEmailValid
import com.practicum.list.core.common.utils.isPasswordWeak
import com.practicum.list.core.data.network.api.AuthApi
import com.practicum.list.core.data.network.codes.BAD_REQUEST_ERROR
import com.practicum.list.core.data.network.codes.DEFAULT_ERROR
import com.practicum.list.core.data.network.codes.OK
import com.practicum.list.core.data.network.dto.CheckTokenRequest
import com.practicum.list.core.data.network.dto.NetworkClient
import com.practicum.list.core.data.network.dto.RecoverPasswordRequest
import com.practicum.list.core.data.network.dto.RefreshTokenRequest
import com.practicum.list.core.data.network.dto.RegisterRequest
import com.practicum.list.core.data.network.dto.Response
import com.practicum.list.core.data.network.dto.UserAuthRequest
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RetrofitNetworkClient(
    private val authApi: AuthApi,
    private val networkConnectionChecker: NetworkConnectionChecker
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response<out Any> {
        val validationError = checkNetworkAndDto(dto)
        if (validationError != null) return validationError

        return withContext(Dispatchers.IO) {
            try {
                val response = executeNetworkCall(dto)
                Response(data = response, resultCode = OK)
            } catch (e: java.io.IOException) {
                Log.w(TAG, "Network request failed", e)
                Response(data = AuthError.NetworkError, resultCode = DEFAULT_ERROR)
            } catch (e: HttpException) {
                val errorType = e.code().mapHttpError(requestDto = dto)
                Response(data = errorType, resultCode = e.code())

            } catch (e: JsonDataException) {
                Log.e(TAG, "Moshi JSON parsing failed", e)
                Response(data = AuthError.Unknown, resultCode = DEFAULT_ERROR)
            }
        }
    }

    private fun checkNetworkAndDto(dto: Any): Response<AuthError>? {
        if (!networkConnectionChecker.isConnected()) {
            return Response(data = AuthError.NetworkError, resultCode = DEFAULT_ERROR)
        }
        return when (dto) {
            is RegisterRequest -> when {
                dto.password.isPasswordWeak(MIN_PASSWORD_LENGTH)
                -> Response(data = AuthError.WeakPassword, resultCode = BAD_REQUEST_ERROR)

                !dto.email.isEmailValid() ->
                    Response(data = AuthError.InvalidEmail, resultCode = BAD_REQUEST_ERROR)

                else -> {
                    null
                }
            }

            is UserAuthRequest -> {
                if (!dto.email.isEmailValid()) {
                    Response(AuthError.InvalidEmail, resultCode = BAD_REQUEST_ERROR)
                } else {
                    null
                }
            }

            is RecoverPasswordRequest -> {
                if (!dto.email.isEmailValid()) {
                    Response(AuthError.InvalidEmail, resultCode = BAD_REQUEST_ERROR)
                } else {
                    null
                }
            }

            else -> {
                null
            }
        }
    }

    private suspend fun executeNetworkCall(dto: Any): Any {
        return when (dto) {
            is RegisterRequest -> authApi.registerUser(dto)
            is UserAuthRequest -> authApi.loginUser(dto)
            is RefreshTokenRequest -> authApi.refreshToken(dto)
            is CheckTokenRequest -> authApi.checkTokenIsValid(dto.token)
            is RecoverPasswordRequest -> authApi.recoverPassword(dto.email)
            else -> throw IllegalArgumentException("Unknown DTO type")
        }
    }

    private companion object {
        const val TAG = "RetrofitNetworkClient"
        const val MIN_PASSWORD_LENGTH = 7
    }
}