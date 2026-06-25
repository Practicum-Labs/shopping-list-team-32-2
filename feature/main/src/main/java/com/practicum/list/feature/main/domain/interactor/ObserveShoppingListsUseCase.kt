package com.practicum.list.feature.main.domain.interactor

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.feature.main.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow

class ObserveShoppingListsUseCase(private val repository: ShoppingListRepository) {
    operator fun invoke(): Flow<List<ShoppingList>> {
        return repository.observeShoppingLists()
    }
}