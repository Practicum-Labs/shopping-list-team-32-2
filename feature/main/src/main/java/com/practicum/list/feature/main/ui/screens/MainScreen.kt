package com.practicum.list.feature.main.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.practicum.list.core.common.domain.ListEntry

@Composable
fun MainScreen(
    itemsList: List<ListEntry>,
    onItemTap: (id: Long) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text("Мэйн скрин")

            itemsList.forEach {
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { onItemTap(it.id) }
                ) {
                    Text("Айтем ${it.name}")
                }
            }
        }
    }
}