package com.practicum.list.feature.list.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.practicum.list.feature.list.presentation.ListIntent
import com.practicum.list.feature.list.presentation.ListState

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    state: ListState,
    onIntent: (ListIntent) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text("Экран списка")
        Text("Переданный id ${state.listId}")
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { onIntent(ListIntent.BackClicked) }
        ) {
            Text("Назад")
        }
    }
}