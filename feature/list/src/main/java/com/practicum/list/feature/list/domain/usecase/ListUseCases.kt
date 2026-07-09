package com.practicum.list.feature.list.domain.usecase

import com.practicum.list.core.common.domain.Product
import com.practicum.list.feature.product.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveProductsByListIdUseCase @Inject constructor(
    private val repository: ListRepository,
) {
    operator fun invoke(listId: Long): Flow<List<Product>> {
        return repository.observeProductsByListId(listId)
    }
}

class DeleteAllProductsUseCase @Inject constructor(
    private val repository: ListRepository,
) {
    suspend operator fun invoke(listId: Long) {
        repository.deleteAllProducts(listId)
    }
}

class DeleteBoughtProductsUseCase @Inject constructor(
    private val repository: ListRepository,
) {
    suspend operator fun invoke(listId: Long) {
        repository.deleteBoughtProducts(listId)
    }
}

class SortProductsAlphabeticallyUseCase @Inject constructor(
    private val repository: ListRepository,
) {
    suspend operator fun invoke(listId: Long): List<Product> {
        return repository.sortProductsAlphabetically(listId)
    }
}

class SortProductsCustomUseCase @Inject constructor(
    private val repository: ListRepository,
) {
    suspend operator fun invoke(listId: Long, newOrder: List<Product>) {
        repository.sortCustom(listId, newOrder)
    }
}

class UpsertProductUseCase @Inject constructor(
    private val repository: ListRepository,
) {
    suspend operator fun invoke(product: Product) {
        repository.upsertProduct(product)
    }
}
