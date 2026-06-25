package com.practicum.list.feature.product.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ListScreen(
    id: Long,
    onBackTap: () -> Unit
) {
    Text("Экран списка")
    Text("Переданный id $id")
    Button(
        onClick = onBackTap
    ) {
        Text("Назад")
    }
}