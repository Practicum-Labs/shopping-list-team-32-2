package com.practicum.list.core.common.domain

sealed interface AuthError {
    data object InvalidEmail : AuthError
    data object InvalidPassword : AuthError
    data object Unauthorized : AuthError
    data object WeakPassword : AuthError
    data object InternalServerError : AuthError
    data object UserAlreadyExists : AuthError
    data object NetworkError : AuthError
    data object Unknown : AuthError
}