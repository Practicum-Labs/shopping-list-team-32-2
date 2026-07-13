package com.practicum.list.feature.list.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.common.domain.Product
import com.practicum.list.core.components.fab.AddFab
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.list.R
import com.practicum.list.feature.list.presentation.ListIntent
import com.practicum.list.feature.list.presentation.ListState
import com.practicum.list.feature.list.ui.components.ListEmptyPlaceholder
import com.practicum.list.feature.list.ui.components.bottomsheet.ListMenu
import com.practicum.list.feature.list.ui.components.bottomsheet.ProductBottomSheet
import com.practicum.list.feature.list.ui.components.formatQuantityLabel
import com.practicum.list.feature.list.ui.components.listitem.ProductListItem

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    state: ListState,
    onIntent: (ListIntent) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            state.isLoading && state.products.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }

            state.isEmpty -> {
                ListEmptyPlaceholder(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-48).dp)
                        .padding(horizontal = 44.dp),
                    title = stringResource(R.string.list_empty_title),
                    message = stringResource(R.string.list_empty_message),
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = state.products,
                        key = { product -> product.id },
                    ) { product ->
                        ProductListItem(
                            name = product.name,
                            quantityLabel = product.formatQuantityLabel(),
                            isChecked = product.isChecked,
                            onCheckedChange = { checked ->
                                onIntent(
                                    ListIntent.ToggleProductChecked(
                                        product,
                                        isChecked = checked
                                    )
                                )
                            },
                            onQuantityClick = {
                                onIntent(ListIntent.ProductQuantityClicked(product.id))
                            },
                            onDelete = {
                                onIntent(ListIntent.DeleteProductClicked(product.id))
                            },
                            onEdit = {
                                onIntent(ListIntent.ProductMenuEditClicked(product))
                            },
                        )
                    }
                }
            }
        }

        if (!state.isLoading) {
            AddFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 56.dp)
                    .zIndex(1f),
                onClick = { onIntent(ListIntent.AddProductClicked) },
            )
        }
        ProductBottomSheet(
            bottomSheetIsVisible = state.productBottomSheetOpened,
            name = state.productBottomSheetState.name,
            onNameChange = { onIntent(ListIntent.ProductNameChanged(it)) },
            quantity = state.productBottomSheetState.quantity,
            onQuantityChange = { onIntent(ListIntent.ProductQuantityChanged(it)) },
            unit = state.productBottomSheetState.unit,
            onUnitClick = { onIntent(ListIntent.ProductUnitChanged(it)) },
            onDismiss = { onIntent(ListIntent.ProductBottomSheetDismissed) },
            onApplyClicked = { onIntent(ListIntent.ProductApplyClicked) },
            isApplyVisible = state.isAddEnabled
        )


        ListMenu(
            onSortClicked = { onIntent(ListIntent.ListMenuAlphabeticalSortClicked) },
            onRemoveAllClicked = { onIntent(ListIntent.ListMenuDeleteAllClicked) },
            onRemoveChecked = { onIntent(ListIntent.ListMenuDeleteCheckedClicked) },
        )
    }
}

private const val PREVIEW_LIST_ID = 1L

private val previewProducts = listOf(
    Product(
        id = 1,
        name = "Карты",
        isChecked = true,
        listId = PREVIEW_LIST_ID,
        quantity = 1f,
        unit = MeasureUnit.Kilogram,
        sortPosition = 0,
    ),
    Product(
        id = 2,
        name = "Деньги",
        isChecked = false,
        listId = PREVIEW_LIST_ID,
        quantity = 10f,
        unit = MeasureUnit.Piece,
        sortPosition = 1,
    ),
    Product(
        id = 3,
        name = "2 ствола",
        isChecked = false,
        listId = PREVIEW_LIST_ID,
        quantity = 1f,
        unit = MeasureUnit.Liter,
        sortPosition = 2,
    ),
    Product(
        id = 4,
        name = "Сыр",
        isChecked = false,
        listId = PREVIEW_LIST_ID,
        quantity = 1f,
        unit = MeasureUnit.Package,
        sortPosition = 3,
    ),
)

private val listStateEmpty = ListState(
    listId = PREVIEW_LIST_ID,
    listTitle = "Продукты",
    isLoading = false,
)

private val listStateLoading = ListState(
    listId = PREVIEW_LIST_ID,
    listTitle = "Продукты",
    isLoading = true,
)

private val listStateFilled = ListState(
    listId = PREVIEW_LIST_ID,
    listTitle = "Продукты",
    products = previewProducts,
    isLoading = false,
)

@Preview(name = "Empty", showBackground = true)
@Composable
private fun ListScreenEmptyPreview() {
    ShoppingListTheme {
        ListScreen(
            state = listStateEmpty,
            onIntent = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
private fun ListScreenLoadingPreview() {
    ShoppingListTheme {
        ListScreen(
            state = listStateLoading,
            onIntent = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(name = "Filled", showBackground = true)
@Composable
private fun ListScreenFilledPreview() {
    ShoppingListTheme {
        ListScreen(
            state = listStateFilled,
            onIntent = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(name = "Filled dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListScreenFilledDarkPreview() {
    ShoppingListTheme(darkTheme = true) {
        ListScreen(
            state = listStateFilled,
            onIntent = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
