package com.practicum.list.feature.main.data.impl

import androidx.room.withTransaction
import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.local.ShoppingDatabase
import com.practicum.list.core.data.local.dao.ProductDao
import com.practicum.list.core.data.local.dao.ShoppingListDao
import com.practicum.list.core.data.local.mapper.toDomain
import com.practicum.list.core.data.local.mapper.toEntity
import com.practicum.list.core.theme.R
import com.practicum.list.core.common.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val database: ShoppingDatabase,
    private val listDao: ShoppingListDao,
    private val productDao: ProductDao,
    private val userSession: UserSession,
) : ShoppingListRepository {
    private val defaultIconRes = R.drawable.ic_list_cart

    override suspend fun updateShoppingList(shoppingList: ShoppingList) {
        listDao.upsert(shoppingList.toEntity(userSession.getUserId()))
    }

    override suspend fun duplicateShoppingList(shoppingListId: Long) {
        val userId = userSession.getUserId()
        database.withTransaction {
            val newListId = duplicateListAndGetId(shoppingListId, userId) ?: return@withTransaction
            duplicateProducts(shoppingListId, newListId)
        }
    }

    override suspend fun deleteShoppingList(shoppingListId: Long) {
        listDao.deleteById(shoppingListId, userSession.getUserId())
    }

    override suspend fun deleteAllShoppingLists() {
        listDao.deleteAllByUserId(userSession.getUserId())
    }

    override fun observeShoppingLists(): Flow<List<ShoppingList>> {
        return userSession.userId
            .flatMapLatest { userId ->
                listDao.observeByUserId(userId)
            }
            .distinctUntilChanged()
            .map { lists -> lists.map { list -> list.toDomain(defaultIconRes) } }
    }

    override fun observeShoppingList(shoppingListId: Long): Flow<ShoppingList?> {
        return userSession.userId
            .flatMapLatest { userId ->
                listDao.observeById(shoppingListId, userId)
            }
            .distinctUntilChanged()
            .map { list -> list?.toDomain(defaultIconRes) }
    }

    override fun observeListTitle(listId: Long): Flow<String> {
        return observeShoppingList(listId)
            .map { shoppingList -> shoppingList?.name.orEmpty() }
    }

    private suspend fun duplicateListAndGetId(shoppingListId: Long, userId: Long): Long? {
        val shoppingListEntity = listDao.getById(shoppingListId, userId) ?: return null
        val duplicateListEntity =
            shoppingListEntity.copy(
                id = DEFAULT_ID,
                name = "$COPY_STRING ${shoppingListEntity.name}",
            )
        return listDao.upsert(duplicateListEntity)
    }

    private suspend fun duplicateProducts(oldListId: Long, newListId: Long) {
        val products = productDao.getByListId(oldListId)
        if (products.isEmpty()) {
            return
        }
        val newProducts = products.map { product ->
            product.copy(id = DEFAULT_ID, listId = newListId)
        }

        productDao.insertAll(newProducts)
    }

    companion object {
        private const val DEFAULT_ID = 0L
        private const val COPY_STRING = "Копия"
    }
}
