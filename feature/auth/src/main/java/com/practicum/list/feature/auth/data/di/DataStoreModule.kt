package com.practicum.list.feature.auth.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.practicum.list.feature.auth.data.EncryptedSessionSerializer
import com.practicum.list.feature.auth.data.UserSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideEncryptedDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserSession> {
        return DataStoreFactory.create(
            serializer = EncryptedSessionSerializer,
            produceFile = { context.dataStoreFile("encrypted_session.pb") },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}
