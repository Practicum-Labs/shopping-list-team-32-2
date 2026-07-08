package com.practicum.list.feature.main.ui.screens

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.components.cards.SwipeableListItem
import com.practicum.list.core.components.dialogs.CustomLayoutDialog
import com.practicum.list.core.components.fab.AddFab
import com.practicum.list.core.components.placeholder.PlaceholderLayout
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.main.R
import com.practicum.list.feature.main.presentation.MainIntent
import com.practicum.list.feature.main.presentation.MainState

@Composable
fun MainScreen(
    state: MainState,
    onIntent: (MainIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dialog = state.createListDialog
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        when {
            state.isLoading && state.isEmpty -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            state.isEmpty -> {
                PlaceholderLayout(
                    modifier = Modifier
                        .padding(horizontal = 28.dp)
                        .align(Alignment.Center),
                    title = stringResource(R.string.placeholder_empty_title),
                    message = stringResource(R.string.placeholder_empty_message)
                )
            }

            else -> {
                val lists = state.lists
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(items = lists, key = { list -> list.id }) { list ->
                        SwipeableListItem(
                            text = list.name,
                            iconResId = list.iconResId,
                            onClick = {
                                onIntent(MainIntent.OpenList(list.id))
                                Log.d("NAVIGATE", "${list.id}")
                            },
                            onDeleteClick = { onIntent(MainIntent.DeleteList(list.id)) },
                            onEditClick = { onIntent(MainIntent.RenameList(list.id)) },
                            onCopyClick = { onIntent(MainIntent.DuplicateList(list.id)) }
                        )
                    }
                }

            }
        }
        if (dialog != null) {
            CustomLayoutDialog(
                titleTextRes = R.string.new_list_dialog_title_text,
                iconRes = R.drawable.ic_docs_add_on,
                primaryButtonTextRes = R.string.new_list_dialog_create_button_text,
                secondaryButtonTextRes = R.string.cancel_general_text,
                textEditLabelRes = R.string.new_list_label_text,
                textEditText = dialog.name,
                interactionSource = interactionSource,
                onConfirm = { onIntent(MainIntent.ConfirmCreateList(dialog.name)) },
                onDismiss = { onIntent(MainIntent.DismissCreateListDialog) },
                onTextChange = { onIntent(MainIntent.CreateListNameChanged(it)) },
                onKeyboardDone = { keyboardController?.hide() },
            )
        }

        if (!state.isLoading) {
            AddFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp)
                    .zIndex(1f),
                onClick = {
                    onIntent(
                        MainIntent.CreateListClicked
                    )
                }
            )
        }
    }
}

private val mainStateEmpty = MainState(
    lists = listOf(),
    isLoading = false
)
private val mainStateLoading = MainState(
    lists = listOf(),
    isLoading = true
)
private val mainStateContent = MainState(
    lists = listOf(
        ShoppingList(
            id = 1,
            name = "Продукты",
            iconResId = com.practicum.list.core.theme.R.drawable.ic_list_cart
        ),
        ShoppingList(
            id = 2,
            name = "Для дачи",
            iconResId = com.practicum.list.core.theme.R.drawable.ic_list_cart
        )
    ),
    isLoading = true
)

@Preview(showSystemUi = true)
@Composable
private fun EmptyStatePreview() {
    ShoppingListTheme {
        MainScreen(
            mainStateEmpty,
            onIntent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingStatePreview() {
    ShoppingListTheme {
        MainScreen(
            mainStateLoading,
            onIntent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ContentStatePreview() {
    ShoppingListTheme {
        MainScreen(
            mainStateContent,
            onIntent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
