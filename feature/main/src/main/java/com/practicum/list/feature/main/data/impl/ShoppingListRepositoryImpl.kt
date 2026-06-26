package com.practicum.list.feature.main.data.impl

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.data.local.dao.ShoppingListDao
import com.practicum.list.core.data.local.mapper.toDomain
import com.practicum.list.core.data.local.mapper.toEntity
import com.practicum.list.feature.main.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ShoppingListRepositoryImpl(private val dao: ShoppingListDao) : ShoppingListRepository {

    override suspend fun updateShoppingList(shoppingList: ShoppingList) {
        val shoppingListEntity = shoppingList.toEntity()
        dao.upsert(shoppingListEntity)
    }

    override suspend fun duplicateShoppingList(shoppingListId: Long) {
        val duplicateListEntity =
            dao.observeById(shoppingListId).first()?.copy(id = shoppingListId + ID_INCREMENT)
        duplicateListEntity?.let { dao.upsert(it) }
    }

    override suspend fun deleteShoppingList(shoppingListId: Long) {
        dao.deleteById(shoppingListId)
    }

    override suspend fun deleteAllShoppingLists() {
        dao.deleteAll()
    }

    override fun observeShoppingLists(): Flow<List<ShoppingList>> {
        return dao.observeAll().distinctUntilChanged()
            .map { lists -> lists.map { list -> list.toDomain(DEFAULT_ICON_RES_ID) } }
    }

    override fun observeShoppingList(shoppingListId: Long): Flow<ShoppingList?> {
        return dao.observeById(shoppingListId).distinctUntilChanged().map { list -> list?.toDomain(DEFAULT_ICON_RES_ID) }
    }

    companion object {
        private const val ID_INCREMENT = 1
        private const val DEFAULT_ICON_RES_ID = 1
    }
}