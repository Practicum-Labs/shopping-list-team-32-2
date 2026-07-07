package com.practicum.list.feature.product.data.impl

import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.local.dao.ProductDao
import com.practicum.list.core.data.local.mapper.toEntity
import com.practicum.list.core.theme.R
import com.practicum.list.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val userSession: UserSession,
) : ProductRepository {

    override suspend fun addProduct(product: Product, listId: Long) {

    }

    override suspend fun updateProduct(product: Product, listId: Long) {
        dao.upsert(product.toEntity(), listId)
    }

    override suspend fun sortProductsAlphabetically(listId: Long) : List<Product> {
        dao.sortListAlphabetically(listId)
    }

    override suspend fun sortCustom(listId: Long, newOrder: List<Product>) {
        val list = dao.

    }

    override suspend fun deleteBoughtProducts(listId: Long) {
        dao.deleteAllCheckedByListId(listId)
    }

    override suspend fun deleteAllProducts(listId: Long) {
        dao.deleteAllByListId(listId)
    }

    override fun observeProductsByListId(listId: Long): Flow<List<Product>>?  {
        return dao.observeByListId(listId)
            ?.distinctUntilChanged()
            ?.map { product -> product.toDomain() }
    }

    override suspend fun duplicateShoppingList(shoppingListId: Long) {
        val userId = userSession.getUserId()
        val shoppingListEntity = dao.getById(shoppingListId, userId)
        val duplicateListEntity =
            shoppingListEntity?.copy(
                id = DEFAULT_ID,
                name = "$COPY_STRING ${shoppingListEntity.name}",
            )
        duplicateListEntity?.let { dao.upsert(it) }
    }

    override suspend fun deleteShoppingList(shoppingListId: Long) {
        dao.deleteById(shoppingListId, userSession.getUserId())
    }

    override suspend fun deleteAllShoppingLists() {
        dao.deleteAllByUserId(userSession.getUserId())
    }

    override fun observeShoppingLists(): Flow<List<ShoppingList>> {
        return userSession.userId
            .flatMapLatest { userId ->
                dao.observeByUserId(userId)
            }
            .distinctUntilChanged()
            .map { lists -> lists.map { list -> list.toDomain(defaultIconRes) } }
    }

    override fun observeShoppingList(shoppingListId: Long): Flow<ShoppingList?> {
        return userSession.userId
            .flatMapLatest { userId ->
                dao.observeById(shoppingListId, userId)
            }
            .distinctUntilChanged()
            .map { list -> list?.toDomain(defaultIconRes) }
    }

    companion object {
        private const val DEFAULT_ID = 0L
        private const val COPY_STRING = "Копия"
    }
}