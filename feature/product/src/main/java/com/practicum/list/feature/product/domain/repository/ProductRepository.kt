package com.practicum.list.feature.product.domain.repository

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun updateProduct(productId: Long, quantity: Float, units: MeasureUnit)
    suspend fun addProduct(product: Product, listId: Long)
    suspend fun sortProductsAlphabetically(listId: Long) : List<Product>?
    suspend fun sortCustom(listId: Long, newOrder: List<Product>)
    suspend fun deleteBoughtProducts(listId: Long)
    suspend fun deleteAllProducts(listId: Long)
    fun observeProductsByListId(listId: Long): Flow<List<Product>?>
}