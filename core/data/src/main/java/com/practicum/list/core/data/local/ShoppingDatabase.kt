package com.practicum.list.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.list.core.data.local.dao.ProductDao
import com.practicum.list.core.data.local.dao.ShoppingListDao
import com.practicum.list.core.data.local.entity.ProductEntity
import com.practicum.list.core.data.local.entity.ShoppingListEntity

@Database(
    entities = [
        ShoppingListEntity::class,
        ProductEntity::class,
    ],
    version = 3,
    exportSchema = false,
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

    abstract fun productDao(): ProductDao
}
