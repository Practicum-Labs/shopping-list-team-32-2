package com.practicum.list.di

import android.app.Application
import dagger.Component
import ru.practicum.list.core.data.di.DatabaseModule
import ru.practicum.list.core.data.di.NetworkModule
import ru.practicum.list.core.data.local.ShoppingDatabase
import ru.practicum.list.core.data.remote.api.ProductApi
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
    ],
)
interface AppComponent {

    fun database(): ShoppingDatabase

    fun productApi(): ProductApi

    @Component.Factory
    interface Factory {

        fun create(@dagger.BindsInstance application: Application): AppComponent
    }
}
