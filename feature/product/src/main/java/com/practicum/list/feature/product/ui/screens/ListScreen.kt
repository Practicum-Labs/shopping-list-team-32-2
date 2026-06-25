package com.practicum.list.feature.product.ui.screens

import android.graphics.Paint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ListScreen(
    id: Long,
    onBackTap: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text("Экран списка")
            Text("Переданный id $id")
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onBackTap
            ) {
                Text("Назад")
            }
        }
    }
}