package com.practicum.list.core.common.domain.usecase

import com.practicum.list.core.common.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveListTitleUseCase @Inject constructor(
    private val repository: ShoppingListRepository,
) {
    operator fun invoke(listId: Long): Flow<String> {
        return repository.observeListTitle(listId)
    }
}
