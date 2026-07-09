package com.practicum.list.feature.product.data.di

import com.practicum.list.feature.product.data.impl.ProductRepositoryImpl
import com.practicum.list.feature.product.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class ProductRepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        productListRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}