package com.practicum.list.feature.main.data.di

import com.practicum.list.feature.main.data.impl.ShoppingListRepositoryImpl
import com.practicum.list.feature.main.domain.repository.ShoppingListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class ShoppingListRepositoryModule {
    @Binds
    abstract fun bindShoppingListRepository(
        shoppingListRepositoryImpl: ShoppingListRepositoryImpl
    ): ShoppingListRepository
}