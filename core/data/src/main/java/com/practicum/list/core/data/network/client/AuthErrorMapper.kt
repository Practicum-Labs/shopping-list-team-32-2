package com.practicum.list.core.data.network.client

import com.practicum.list.core.common.domain.AuthError
import com.practicum.list.core.data.network.codes.BAD_REQUEST_ERROR
import com.practicum.list.core.data.network.codes.CONFLICT_ERROR
import com.practicum.list.core.data.network.codes.SERVER_ERROR
import com.practicum.list.core.data.network.codes.UNAUTHORIZED_ERROR
import com.practicum.list.core.data.network.dto.RecoverPasswordRequest
import com.practicum.list.core.data.network.dto.RegisterRequest
import com.practicum.list.core.data.network.dto.UserAuthRequest

fun Int.mapHttpError(requestDto: Any): AuthError {
    return when (this) {
        UNAUTHORIZED_ERROR -> AuthError.Unauthorized
        CONFLICT_ERROR -> handleConflictError(requestDto)
        SERVER_ERROR -> AuthError.InternalServerError
        BAD_REQUEST_ERROR -> handleBadRequestError(requestDto)
        else -> AuthError.Unknown
    }
}

private fun handleConflictError(requestDto: Any): AuthError {
    return if (requestDto is RegisterRequest) {
        AuthError.UserAlreadyExists
    } else {
        AuthError.Unknown
    }
}

private fun handleBadRequestError(requestDto: Any): AuthError {
    return when (requestDto) {
        is UserAuthRequest -> AuthError.InvalidPassword
        is RegisterRequest -> AuthError.InvalidEmail
        is RecoverPasswordRequest -> AuthError.InvalidEmail
        else -> AuthError.Unknown
    }
}
