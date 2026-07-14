package com.practicum.list.core.data.di

import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.SessionEvents
import com.practicum.list.core.data.SessionEventsImpl
import com.practicum.list.core.data.session.UserSessionStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindUserSession(impl: UserSessionStore): UserSession

    @Binds
    @Singleton
    abstract fun bindSessionEvents(impl: SessionEventsImpl): SessionEvents
}
