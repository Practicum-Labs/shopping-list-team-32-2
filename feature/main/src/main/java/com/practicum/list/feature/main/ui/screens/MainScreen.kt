package com.practicum.list.feature.main.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.practicum.list.core.common.domain.ListEntry

@Composable
fun MainScreen(
    itemsList: List<ListEntry>,
    onItemTap: (id: Long) -> Unit
) {
    Text("Мэйн скрин")

    itemsList.forEach {
        Button(
            onClick = { onItemTap(it.id) }
        ) {
            Text("Айтем ${it.name}")
        }
    }
}