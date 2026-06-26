package com.practicum.list.feature.main.data.impl

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.data.local.dao.ShoppingListDao
import com.practicum.list.core.data.local.mapper.toDomain
import com.practicum.list.core.data.local.mapper.toEntity
import com.practicum.list.core.theme.R
import com.practicum.list.feature.main.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val dao: ShoppingListDao
) : ShoppingListRepository {
    private val defaultIconRes = R.drawable.ic_list_cart

    override suspend fun updateShoppingList(shoppingList: ShoppingList) {
        val shoppingListEntity = shoppingList.toEntity()
        dao.upsert(shoppingListEntity)
    }

    override suspend fun duplicateShoppingList(shoppingListId: Long) {
        val shoppingListEntity = dao.getById(shoppingListId)
        val duplicateListEntity =
            shoppingListEntity?.copy(
                id = DEFAULT_ID,
                name = "$COPY_STRING ${shoppingListEntity.name}"
            )
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
            .map { lists -> lists.map { list -> list.toDomain(defaultIconRes) } }
    }

    override fun observeShoppingList(shoppingListId: Long): Flow<ShoppingList?> {
        return dao.observeById(shoppingListId).distinctUntilChanged()
            .map { list -> list?.toDomain(defaultIconRes) }
    }

    companion object {
        private const val DEFAULT_ID = 0L
        private const val COPY_STRING = "Копия"
    }
}