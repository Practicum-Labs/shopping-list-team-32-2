package com.practicum.list.feature.main.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.theme.R
import com.practicum.list.core.theme.ui.CustomLayoutDialog
import com.practicum.list.core.theme.ui.SwipeableListItem

@Composable
fun MainScreen(
    itemsList: List<ShoppingList>,
    onItemTap: (id: Long) -> Unit,
    onAddClick: () -> Unit
) {
    val shouldShowDialog = remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            Text("Мэйн скрин")

                itemsList.forEach { item ->

                    SwipeableListItem(
                        iconResId = R.drawable.ic_list_cart,
                        text = item.name,
                        { onItemTap(item.id) }, {}, {}, {}
                    )
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
