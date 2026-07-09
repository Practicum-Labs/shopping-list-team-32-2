package com.practicum.list.feature.main.domain.usecase

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.feature.main.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteShoppingListUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(shoppingListId: Long) {
        repository.deleteShoppingList(shoppingListId)
    }
}

class DuplicateShoppingListUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(shoppingListId: Long) {
        repository.duplicateShoppingList(shoppingListId)
    }
}

class ObserveShoppingListsUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    operator fun invoke(): Flow<List<ShoppingList>> {
        return repository.observeShoppingLists()
    }
}

class ObserveShoppingListUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    operator fun invoke(shoppingListId: Long): Flow<ShoppingList?> {
        return repository.observeShoppingList(shoppingListId)
    }
}

class UpsertShoppingListUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(shoppingList: ShoppingList) {
        repository.updateShoppingList(shoppingList)
    }
}

class ObserveShoppingListTitleUseCase @Inject constructor(
    private val repository: ShoppingListRepository,
) {
    operator fun invoke(listId: Long): Flow<String> {
        return repository.observeShoppingList(listId)
            .map { shoppingList -> shoppingList?.name.orEmpty() }
    }
}
