package com.practicum.list.feature.list.ui.components.bottomsheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.practicum.list.feature.list.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListMenu(
    modifier: Modifier = Modifier,
    onSortClicked: () -> Unit,
    onRemoveAllClicked: () -> Unit,
    onRemoveChecked: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(true) }

    fun animateAndDismiss(onClick: () -> Unit) {
        onClick()
        showBottomSheet = false
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            MenuListItem(
                text = R.string.sort_alphabetically,
                icon = R.drawable.ic_arrows_24,
                onClick = { animateAndDismiss(onSortClicked) },
            )
            MenuListItem(
                text = R.string.remove_all,
                icon = R.drawable.ic_delete_24,
                onClick = { animateAndDismiss(onRemoveAllClicked) },
            )
            MenuListItem(
                text = R.string.remove_bought,
                icon = R.drawable.ic_clear_24,
                onClick = { animateAndDismiss(onRemoveChecked) },
            )

        }
    }
}

@Composable
private fun MenuListItem(@StringRes text: Int, @DrawableRes icon: Int, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick() }
            ),
        headlineContent = {
            Text(
                text = stringResource(text),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        leadingContent = {
            Icon(
                painterResource(icon),
                contentDescription = stringResource(text),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}

