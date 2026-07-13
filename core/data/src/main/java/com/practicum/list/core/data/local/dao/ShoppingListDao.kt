package com.practicum.list.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.list.core.data.local.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_lists WHERE user_id = :userId ORDER BY id")
    fun observeByUserId(userId: Long): Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM shopping_lists WHERE id = :id AND user_id = :userId")
    fun observeById(id: Long, userId: Long): Flow<ShoppingListEntity?>

    @Query("SELECT * FROM shopping_lists WHERE id = :id AND user_id = :userId")
    suspend fun getById(id: Long, userId: Long): ShoppingListEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ShoppingListEntity): Long

    @Query("DELETE FROM shopping_lists WHERE id = :id AND user_id = :userId")
    suspend fun deleteById(id: Long, userId: Long)

    @Query("DELETE FROM shopping_lists WHERE user_id = :userId")
    suspend fun deleteAllByUserId(userId: Long)

    @Query("UPDATE shopping_lists SET user_id = :toUserId WHERE user_id = :fromUserId")
    suspend fun migrateUserId(fromUserId: Long, toUserId: Long)
}
