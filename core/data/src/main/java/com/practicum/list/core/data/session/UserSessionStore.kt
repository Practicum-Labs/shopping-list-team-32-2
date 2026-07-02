package com.practicum.list.core.data.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.common.domain.UserSessionDefaults
import com.practicum.list.core.data.local.dao.ShoppingListDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.authSessionDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "auth_session",
)

@Singleton
class UserSessionStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val shoppingListDao: ShoppingListDao,
) : UserSession {

    override val userId: Flow<Long> = context.authSessionDataStore.data.map { preferences ->
        preferences[KEY_USER_ID] ?: UserSessionDefaults.LEGACY_LOCAL_USER_ID
    }

    override suspend fun getUserId(): Long =
        context.authSessionDataStore.data.first()[KEY_USER_ID]
            ?: UserSessionDefaults.LEGACY_LOCAL_USER_ID

    override suspend fun getAccessToken(): String? =
        context.authSessionDataStore.data.first()[KEY_ACCESS_TOKEN]

    override suspend fun getRefreshToken(): String? =
        context.authSessionDataStore.data.first()[KEY_REFRESH_TOKEN]

    override suspend fun saveSession(userId: Long, accessToken: String, refreshToken: String) {
        val previousUserId = getUserId()
        context.authSessionDataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
            preferences[KEY_ACCESS_TOKEN] = accessToken
            preferences[KEY_REFRESH_TOKEN] = refreshToken
        }
        if (
            previousUserId == UserSessionDefaults.LEGACY_LOCAL_USER_ID &&
            userId != UserSessionDefaults.LEGACY_LOCAL_USER_ID
        ) {
            shoppingListDao.migrateUserId(
                fromUserId = UserSessionDefaults.LEGACY_LOCAL_USER_ID,
                toUserId = userId,
            )
        }
    }

    override suspend fun clearSession() {
        context.authSessionDataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_ACCESS_TOKEN)
            preferences.remove(KEY_REFRESH_TOKEN)
        }
    }

    private companion object {
        val KEY_USER_ID = longPreferencesKey("user_id")
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}
