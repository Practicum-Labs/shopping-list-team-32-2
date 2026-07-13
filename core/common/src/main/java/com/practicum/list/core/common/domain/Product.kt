package com.practicum.list.core.common.domain

data class Product(
    val id: Long,
    val name: String,
    val isChecked: Boolean,
    val listId: Long,
    val quantity: Float,
    val unit: MeasureUnit,
    val sortPosition: Int
)