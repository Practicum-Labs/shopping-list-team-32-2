package com.practicum.list.di

import android.app.Application
import dagger.Component
import com.practicum.list.core.data.di.DatabaseModule
import com.practicum.list.core.data.di.NetworkModule
import com.practicum.list.core.data.local.ShoppingDatabase
import com.practicum.list.core.data.remote.api.ProductApi
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
