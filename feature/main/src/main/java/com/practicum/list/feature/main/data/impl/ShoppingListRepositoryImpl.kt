package com.practicum.list.feature.main.data.impl

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.data.local.dao.ShoppingListDao
import com.practicum.list.feature.main.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow

class ShoppingListRepositoryImpl(val dao: ShoppingListDao): ShoppingListRepository {
    override suspend fun updateShoppingList(shoppingList: ShoppingList) {
        TODO("Not yet implemented")
    }

    override suspend fun duplicateShoppingList(shoppingListId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteShoppingList(shoppingListId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllShoppingLists() {
        TODO("Not yet implemented")
    }

    override fun observeShoppingLists(): Flow<List<ShoppingList>> {
        TODO("Not yet implemented")
    }

    override fun observeShoppingList(shoppingListId: Long): Flow<ShoppingList> {
        TODO("Not yet implemented")
    }
}