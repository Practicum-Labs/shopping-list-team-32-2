package com.practicum.list.core.common.domain

data class Product (
    val id: Long,
    val name: String,
    val isChecked: Boolean,
    val listId: Long,
    val quantity: Int,
    val unit: MeasureUnit
)