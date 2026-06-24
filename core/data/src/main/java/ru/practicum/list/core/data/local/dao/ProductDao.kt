package ru.practicum.list.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.list.core.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products WHERE listId = :listId ORDER BY id")
    fun observeByListId(listId: Long): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ProductEntity): Long
}
