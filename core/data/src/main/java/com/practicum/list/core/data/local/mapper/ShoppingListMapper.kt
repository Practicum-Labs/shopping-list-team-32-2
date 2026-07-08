package com.practicum.list.core.data.local.mapper

import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.data.local.entity.ShoppingListEntity

fun ShoppingListEntity.toDomain(defaultIconResId: Int): ShoppingList =
    ShoppingList(
        id = id,
        name = name,
        iconResId = iconResId.takeIf { it != 0 } ?: defaultIconResId,
        products = listOf()
    )

fun ShoppingList.toEntity(userId: Long): ShoppingListEntity =
    ShoppingListEntity(
        id = id,
        userId = userId,
        name = name,
        iconResId = iconResId,
    )
