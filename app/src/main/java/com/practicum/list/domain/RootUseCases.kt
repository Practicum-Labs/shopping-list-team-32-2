package com.practicum.list.domain

import com.practicum.list.feature.auth.domain.AuthRepository
import com.practicum.list.feature.auth.domain.models.RefreshResult
import com.practicum.list.feature.auth.domain.models.TokenValidResult
import javax.inject.Inject

class CheckTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(token: String): TokenValidResult {
        return repository.checkTokenIsValid(token)
    }
}

class RefreshTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(): RefreshResult {
        return repository.refreshToken()
    }
}
