package com.practicum.list.feature.main.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.cards.SwipeableListItem
import com.practicum.list.feature.main.presentation.MainIntent
import com.practicum.list.feature.main.presentation.MainState
import com.practicum.list.core.theme.ui.CustomLayoutDialog

@Composable
fun MainScreen(
    state: MainState,
    onIntent: (MainIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shouldShowDialog = remember { mutableStateOf(false) }

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
                        onClick = { },
                        onDeleteClick = { },
                        onEditClick = { },
                        onCopyClick = { }
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

            CustomLayoutDialog(
                isVisible = shouldShowDialog.value,
                titleTextRes = R.string.new_list_dialog_title_text,
                iconRes = R.drawable.docs_add_on,
                primaryButtonTextRes = R.string.cancel_general_text ,
                secondaryButtonTextRes = R.string.new_list_dialog_create_button_text,
                textEditLabelRes = R.string.new_list_label_text,
                textEditPlaceholderTextRes = R.string.new_list_placeholder_text,
                textEditText = "????",
                onConfirm = { shouldShowDialog.value = false },
                onDismiss = { shouldShowDialog.value = false },
                onTextChange = { }
            )

            Button(
                {
                    onAddClick()
                    shouldShowDialog.value = true
                },
                enabled = true,
            ) {
                Text("Добавить")
            }
        }
    }
}
