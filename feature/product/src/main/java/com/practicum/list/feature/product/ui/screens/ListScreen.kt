package com.practicum.list.feature.product.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import domain.ListEntry

@Composable
fun ListScreen(list: ListEntry) {
    Text(list.name)
}