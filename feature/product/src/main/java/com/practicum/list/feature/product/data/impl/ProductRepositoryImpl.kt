package com.practicum.list.feature.product.data.impl

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.local.dao.ProductDao
import com.practicum.list.core.data.local.mapper.toDomain
import com.practicum.list.core.data.local.mapper.toEntity
import com.practicum.list.core.theme.R
import com.practicum.list.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao
) : ProductRepository {

    override suspend fun upsertProduct(product: Product) {
        dao.upsertProduct(product.toEntity())
    }

    override suspend fun sortProductsAlphabetically(listId: Long) : List<Product> {
        return dao.getByListId(listId)
            .sortedBy { it.name }
            .map { it.toDomain() }
    }

    override suspend fun sortCustom(listId: Long, newOrder: List<Product>) {
        newOrder.forEach { index ->
            dao.updateListPosition(index.id, index.sortPosition)
        }
    }

    override suspend fun deleteBoughtProducts(listId: Long) {
        dao.deleteAllCheckedByListId(listId)
    }

    override suspend fun deleteAllProducts(listId: Long) {
        dao.deleteAllByListId(listId)
    }

    override fun observeProductsByListId(listId: Long): Flow<List<Product>>  {
        return dao.observeByListId(listId)
            .distinctUntilChanged()
            .map { products -> products.map { it.toDomain() } }
    }
}