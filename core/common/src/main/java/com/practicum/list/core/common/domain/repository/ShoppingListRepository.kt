package com.practicum.list.core.common.domain.repository

import com.practicum.list.core.common.domain.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun updateShoppingList(shoppingList: ShoppingList)
    suspend fun duplicateShoppingList(shoppingListId: Long)
    suspend fun deleteShoppingList(shoppingListId: Long)
    suspend fun deleteAllShoppingLists()
    fun observeShoppingLists(): Flow<List<ShoppingList>>
    fun observeShoppingList(shoppingListId: Long): Flow<ShoppingList?>
    fun observeListTitle(listId: Long): Flow<String>
}
