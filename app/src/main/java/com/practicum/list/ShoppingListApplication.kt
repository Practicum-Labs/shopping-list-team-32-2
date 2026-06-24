package com.practicum.list

import android.app.Application
import com.practicum.list.di.AppComponent
import com.practicum.list.di.DaggerAppComponent

class ShoppingListApplication : Application() {

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = requireNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create(this)
    }
}
