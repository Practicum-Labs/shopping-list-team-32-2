package com.practicum.list.feature.list.data.impl

import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.local.dao.ProductDao
import com.practicum.list.core.data.local.dao.ShoppingListDao
import com.practicum.list.core.data.local.mapper.toDomain
import com.practicum.list.core.data.local.mapper.toEntity
import com.practicum.list.feature.list.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val shoppingListDao: ShoppingListDao,
    private val userSession: UserSession,
) : ListRepository {

    override suspend fun upsertProduct(product: Product) {
        productDao.upsertProduct(product.toEntity())
    }

    override suspend fun deleteProduct(productId: Long) {
        productDao.deleteProduct(productId)
    }

    override suspend fun sortProductsAlphabetically(listId: Long): List<Product> {
        val sortedEntities = productDao.getByListIdOrderByName(listId)
        sortedEntities.forEachIndexed { index, entity ->
            if (entity.sortPosition != index) {
                productDao.updateListPosition(entity.id, index)
            }
        }
        return sortedEntities.mapIndexed { index, entity ->
            entity.copy(sortPosition = index).toDomain()
        }
    }

    override suspend fun sortCustom(listId: Long, newOrder: List<Product>) {
        newOrder
            .filter { product -> product.listId == listId }
            .forEach { product ->
                productDao.updateListPosition(product.id, product.sortPosition)
            }
    }

    override suspend fun deleteBoughtProducts(listId: Long) {
        productDao.deleteAllCheckedByListId(listId)
    }

    override suspend fun deleteAllProducts(listId: Long) {
        productDao.deleteAllByListId(listId)
    }

    override fun observeProductsByListId(listId: Long): Flow<List<Product>> {
        return productDao.observeByListId(listId)
            .distinctUntilChanged()
            .map { products -> products.map { it.toDomain() } }
    }

    override fun observeListTitle(listId: Long): Flow<String> {
        return userSession.userId
            .flatMapLatest { userId ->
                shoppingListDao.observeById(listId, userId)
            }
            .distinctUntilChanged()
            .map { shoppingList -> shoppingList?.name.orEmpty() }
    }
}
