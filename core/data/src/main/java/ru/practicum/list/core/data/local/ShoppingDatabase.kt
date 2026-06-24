package ru.practicum.list.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.list.core.data.local.dao.ProductDao
import ru.practicum.list.core.data.local.dao.ShoppingListDao
import ru.practicum.list.core.data.local.entity.ProductEntity
import ru.practicum.list.core.data.local.entity.ShoppingListEntity

@Database(
    entities = [
        ShoppingListEntity::class,
        ProductEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

    abstract fun productDao(): ProductDao
}
