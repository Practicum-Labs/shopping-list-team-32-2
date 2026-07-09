package com.practicum.list.feature.product.domain.usecase

import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.common.domain.UserSession
import com.practicum.list.core.data.local.dao.ShoppingListDao
import com.practicum.list.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveProductsByListIdUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(listId: Long): Flow<List<Product>> {
        return repository.observeProductsByListId(listId)
    }
}

class DeleteAllProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(listId: Long) {
        repository.deleteAllProducts(listId)
    }
}

class DeleteBoughtProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(listId: Long) {
        return repository.deleteBoughtProducts(listId)
    }
}

class SortProductsAlphabeticallyUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(listId: Long): List<Product> {
        return repository.sortProductsAlphabetically(listId)
    }
}

class SortProductsCustomUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(listId: Long, newOrder: List<Product>) {
        return repository.sortCustom(listId, newOrder)
    }
}

class UpsertProductUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        repository.upsertProduct(product)
    }
}

class ObserveShoppingListTitleUseCase @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
    private val userSession: UserSession
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(listId: Long): Flow<String> {
        return userSession.userId.flatMapLatest { userId ->
            shoppingListDao.observeById(listId, userId)
                .map { it?.name.orEmpty() }
        }
    }
}
