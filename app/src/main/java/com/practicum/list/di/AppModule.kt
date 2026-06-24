package com.practicum.list.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides
    fun provideContext(application: Application): Context =
        application.applicationContext
}
