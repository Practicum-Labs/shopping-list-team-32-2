package com.practicum.list.feature.auth.data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: DataStore<UserSession>
) {
    val userSessionFlow: Flow<UserSession> = dataStore.data

    suspend fun saveSession(token: String, userId: String) {
        dataStore.updateData { currentSession ->
            currentSession.copy(token = token, userId = userId)
        }
    }

    suspend fun clearSession() {
        dataStore.updateData { UserSession() }
    }
}
