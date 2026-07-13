package com.practicum.list.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.practicum.list.core.data.local.converter.MeasureUnitConverter
import com.practicum.list.core.data.local.dao.ProductDao
import com.practicum.list.core.data.local.dao.ShoppingListDao
import com.practicum.list.core.data.local.entity.ProductEntity
import com.practicum.list.core.data.local.entity.ShoppingListEntity

@Database(
    entities = [
        ShoppingListEntity::class,
        ProductEntity::class,
    ],
    version = 5,
    exportSchema = false,
)
@TypeConverters(MeasureUnitConverter::class)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

    abstract fun productDao(): ProductDao
}
