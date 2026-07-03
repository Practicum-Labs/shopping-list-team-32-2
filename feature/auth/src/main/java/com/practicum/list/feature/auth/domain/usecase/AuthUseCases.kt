package com.practicum.list.feature.auth.domain.usecase

import com.practicum.list.feature.auth.domain.AuthRepository
import javax.inject.Inject

class RegisterUserAuthUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) {
        repository.registerUser(email, password)
    }
}

class LogoutUserAuthUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) {
        repository.loginUser(email, password)
    }
}

class RefreshTokenAuthUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.refreshToken()
    }
}

class CheckTokenAuthUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(token: String) {
        repository.checkTokenIsValid(token)
    }
}

class RecoverPasswordAuthUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String) {
        repository.recoverPassword(email)
    }
}