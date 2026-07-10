package com.practicum.list.core.data.local.mapper

import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.data.local.entity.ProductEntity

fun ProductEntity.toDomain(): Product =
    Product(
        id = id,
        listId = listId,
        name = name,
        quantity = quantity,
        isChecked = isChecked,
        unit = unit,
        sortPosition = sortPosition
    )

fun Product.toEntity(): ProductEntity =
    ProductEntity(
        id = id,
        listId = listId,
        name = name,
        quantity = quantity,
        isChecked = isChecked,
        unit = unit,
        sortPosition = sortPosition
    )
