package com.practicum.list.feature.list.domain.repository

import com.practicum.list.core.common.domain.Product
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    suspend fun upsertProduct(product: Product)
    suspend fun sortProductsAlphabetically(listId: Long) : List<Product>
    suspend fun sortCustom(listId: Long, newOrder: List<Product>)
    suspend fun deleteBoughtProducts(listId: Long)
    suspend fun deleteAllProducts(listId: Long)
    suspend fun deleteProduct(productId: Long)
    fun observeProductsByListId(listId: Long): Flow<List<Product>>
    fun observeListTitle(listId: Long): Flow<String>
}