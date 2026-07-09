package com.practicum.list.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.data.local.entity.ProductEntity
import com.practicum.list.core.data.local.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE listId = :listId ORDER BY id")
    fun observeByListId(listId: Long): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProduct(product: ProductEntity)

    @Query("SELECT * FROM products WHERE listId = :listId")
    suspend fun getByListId(listId: Long): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ProductEntity): Long

    @Query("UPDATE products SET sortPosition=:sortPosition WHERE id = :productId")
    suspend fun updateListPosition(productId: Long, sortPosition: Int)

    @Query("DELETE FROM products WHERE listId = :listId")
    suspend fun deleteAllByListId(listId: Long)
    @Query("DELETE FROM products WHERE listId = :listId AND isChecked = 1")
    suspend fun deleteAllCheckedByListId(listId: Long)
}
