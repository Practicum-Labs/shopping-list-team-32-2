package com.practicum.list.feature.main.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.cards.SwipeableListItem
import com.practicum.list.feature.main.presentation.MainIntent
import com.practicum.list.feature.main.presentation.MainState

@Composable
fun MainScreen(
    state: MainState,
    onIntent: (MainIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            state.isLoading && state.isEmpty -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.isEmpty -> {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text("Нет списков покупок")
                    Text("Временный пример айтема")
                    SwipeableListItem(
                            text = "Первый элемент",
                            iconResId = com.practicum.list.core.theme.R.drawable.ic_list_cart,
                            onClick = {  },
                            onDeleteClick = {  },
                            onEditClick = {  },
                            onCopyClick = {  }
                    )
                    Button(onClick = { onIntent(MainIntent.CreateListClicked) }) {
                        Text("Создать список")
                    }
                }
            }

            else -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(onClick = { onIntent(MainIntent.CreateListClicked) }) {
                        Text("Создать список")
                    }

                    state.lists.forEach { item ->
                        SwipeableListItem(
                            text = item.name,
                            iconResId = item.iconResId,
                            onClick = { onIntent(MainIntent.OpenList(item.id)) },
                            onDeleteClick = { onIntent(MainIntent.DeleteList(item.id)) },
                            onEditClick = { onIntent(MainIntent.RenameList(item.id)) },
                            onCopyClick = { onIntent(MainIntent.DuplicateList(item.id)) }
                        )
                    }
                }
            }
        }
    }
}
