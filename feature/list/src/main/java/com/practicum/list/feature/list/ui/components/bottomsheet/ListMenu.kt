package com.practicum.list.feature.list.ui.components.bottomsheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.practicum.list.feature.list.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListMenu(
    modifier: Modifier = Modifier,
    isBottomSheetOpened: Boolean = false,
    onSortClicked: () -> Unit,
    onRemoveAllClicked: () -> Unit,
    onRemoveChecked: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (isBottomSheetOpened) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            MenuListItem(
                text = R.string.sort_alphabetically,
                icon = R.drawable.ic_arrows_24,
                onClick = onSortClicked,
            )
            MenuListItem(
                text = R.string.remove_all,
                icon = R.drawable.ic_delete_24,
                onClick = onRemoveAllClicked,
            )
            MenuListItem(
                text = R.string.remove_bought,
                icon = R.drawable.ic_clear_24,
                onClick = onRemoveChecked,
            )
        }
    }
}

@Composable
private fun MenuListItem(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
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
