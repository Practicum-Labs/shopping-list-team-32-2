package ru.practicum.list.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.list.core.data.local.entity.ShoppingListEntity

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shopping_lists ORDER BY id")
    fun observeAll(): Flow<List<ShoppingListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ShoppingListEntity): Long
}
