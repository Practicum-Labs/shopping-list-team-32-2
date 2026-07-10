package com.practicum.list.feature.list.data.di

import com.practicum.list.feature.list.data.impl.ListRepositoryImpl
import com.practicum.list.feature.list.domain.repository.ListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class ListRepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        productListRepositoryImpl: ListRepositoryImpl
    ): ListRepository
}