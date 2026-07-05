package com.practicum.list.feature.auth.domain.usecase

import com.practicum.list.feature.auth.domain.AuthRepository
import com.practicum.list.feature.auth.domain.models.AuthResult
import com.practicum.list.feature.auth.domain.models.RecoverResult
import com.practicum.list.feature.auth.domain.models.RegisterResult
import javax.inject.Inject

class LoginUserAuthUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): AuthResult =
        repository.loginUser(email, password)
}

class RegisterUserAuthUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): RegisterResult =
        repository.registerUser(email, password)
}

class RecoverPasswordAuthUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String): RecoverResult =
        repository.recoverPassword(email)
}
