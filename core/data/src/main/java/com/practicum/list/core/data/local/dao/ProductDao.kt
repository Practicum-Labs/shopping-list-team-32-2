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
    fun observeByListId(listId: Long): Flow<List<ProductEntity>?>

    fun getByListId(listId: Long): List<ProductEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ProductEntity): Long

    @Query("UPDATE products SET quantity=:quantity WHERE id = :productId")
    suspend fun updateQuantity(productId: Long, quantity: Float, unit: MeasureUnit)

    @Query("UPDATE products SET listId=:listId WHERE id = :productId")
    suspend fun updateListPosition(productId: Long, listId: Long)

    suspend fun delete(productId: Long)

    @Query("SELECT * FROM products WHERE listId = :listId ORDER BY name ASC")
    suspend fun sortByName(listId: Long)

    @Query("DELETE FROM products WHERE listId = :listId")
    suspend fun deleteAllByListId(listId: Long)
    @Query("DELETE FROM products WHERE listId = :listId AND isChecked = 1")
    suspend fun deleteAllCheckedByListId(listId: Long)
}
