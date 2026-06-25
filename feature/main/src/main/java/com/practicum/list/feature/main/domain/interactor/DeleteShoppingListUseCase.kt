package com.practicum.list.feature.main.domain.interactor

import com.practicum.list.feature.main.domain.repository.ShoppingListRepository

class DeleteShoppingListUseCase(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(shoppingListId: Long) {
        repository.deleteShoppingList(shoppingListId)
    }
}
