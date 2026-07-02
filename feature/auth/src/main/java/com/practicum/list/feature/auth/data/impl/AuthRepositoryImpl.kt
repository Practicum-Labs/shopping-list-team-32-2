package com.practicum.list.feature.auth.data.impl

class AuthRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val userSession: UserSession,
) : AuthRepository {
    override suspend fun loginUser(email: String, password: String) {
        val response = networkClient.doRequest(UserAuthRequest(email, password))
        // маппинг Response → AuthError / success
        // при успехе:
        // userSession.saveSession(userId, accessToken, refreshToken)
    }
    override suspend fun refreshToken() {
        val refreshToken = userSession.getRefreshToken() ?: return
        networkClient.doRequest(RefreshTokenRequest(refreshToken))
        // обновить токены в userSession
    }
    // ...
}