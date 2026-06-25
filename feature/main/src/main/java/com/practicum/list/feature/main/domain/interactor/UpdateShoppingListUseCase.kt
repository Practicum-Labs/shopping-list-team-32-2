package com.practicum.list.feature.main.domain.interactor

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.feature.main.domain.repository.ShoppingListRepository

class UpdateShoppingListUseCase(private val repository: ShoppingListRepository){
    suspend operator fun invoke(shoppingList: ShoppingList){
        repository.updateShoppingList(shoppingList)
    }
}
