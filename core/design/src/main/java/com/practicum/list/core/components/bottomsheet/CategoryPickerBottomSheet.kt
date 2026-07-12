package com.practicum.list.core.components.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.buttons.RoundIconButton
import com.practicum.list.core.theme.R
import com.practicum.list.core.theme.ShoppingListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPickerBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onIconClick: (Int) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    BottomSheet(
        modifier = Modifier,
        sheetState = sheetState,
        onDismiss = onDismiss
    ) {
        Content(
            onIconClick = onIconClick
        )
    }
}

@Composable
private fun Content(
    onIconClick: (Int) -> Unit = {}
) {
    val context = LocalContext.current

    // к сожалению нормально получить drawable ресурсы из array в xml нельзя
    // нужен апи 31 для TypedArray, а у нас минимальный 24
    val iconList = remember {
        listOf(
            R.drawable.ic_list_icon_1,
            R.drawable.ic_list_icon_2,
            R.drawable.ic_list_icon_3,
            R.drawable.ic_list_icon_4,
            R.drawable.ic_list_icon_5,
            R.drawable.ic_list_icon_6,
            R.drawable.ic_list_icon_7,
            R.drawable.ic_list_icon_8,
            R.drawable.ic_list_icon_9,
            R.drawable.ic_list_icon_10,
            R.drawable.ic_list_icon_11,
            R.drawable.ic_list_icon_12,
            R.drawable.ic_list_icon_13,
            R.drawable.ic_list_icon_14,
            R.drawable.ic_list_icon_15,
            R.drawable.ic_list_icon_16,
            R.drawable.ic_list_icon_17,
            R.drawable.ic_list_icon_18,
            R.drawable.ic_list_icon_19,
            R.drawable.ic_list_icon_20,
            R.drawable.ic_list_icon_21,
            R.drawable.ic_list_icon_22,
            R.drawable.ic_list_icon_23,
            R.drawable.ic_list_icon_24,
            R.drawable.ic_list_icon_25,
            R.drawable.ic_list_icon_26,
            R.drawable.ic_list_icon_27,
            R.drawable.ic_list_icon_28,
            R.drawable.ic_list_icon_29,
            R.drawable.ic_list_icon_30
        )
    }

    val iconDescriptions = remember {
        context.resources.getStringArray(R.array.list_icon_description).toList()
    }

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .padding(top = 16.dp),
        columns = GridCells.Fixed(5),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(iconList.zip(iconDescriptions)) { (resId, description) ->
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                RoundIconButton(
                    resId = resId,
                    onClick = {
                        onIconClick(resId)
                    },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentDescription = description
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun CategoryPickerBottomSheetPreviewLight() {

    ShoppingListTheme(darkTheme = false) {
        CategoryPickerBottomSheet()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun CategoryPickerBottomSheetPreviewDark() {

    ShoppingListTheme(darkTheme = true) {
        CategoryPickerBottomSheet()
    }
}