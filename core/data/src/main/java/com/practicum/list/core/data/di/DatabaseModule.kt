package com.practicum.list.core.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import com.practicum.list.core.data.local.MIGRATION_1_2
import com.practicum.list.core.data.local.ShoppingDatabase
import com.practicum.list.core.data.local.dao.ProductDao
import com.practicum.list.core.data.local.dao.ShoppingListDao
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "shopping_list.db"

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ShoppingDatabase =
        Room.databaseBuilder(
            context,
            ShoppingDatabase::class.java,
            DATABASE_NAME,
        )
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideShoppingListDao(database: ShoppingDatabase): ShoppingListDao =
        database.shoppingListDao()

    @Provides
    fun provideProductDao(database: ShoppingDatabase): ProductDao =
        database.productDao()
}
