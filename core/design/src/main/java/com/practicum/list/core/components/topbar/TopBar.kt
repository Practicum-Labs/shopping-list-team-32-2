package com.practicum.list.core.components.topbar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.R.drawable
import com.practicum.list.core.theme.R.string
import com.practicum.list.core.theme.ShoppingListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateBack: (() -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
    onThemeClick: (() -> Unit)? = null,
    onOptionsClick: (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = { TopBarText(title) },
        navigationIcon = {
            onNavigateBack?.let {
                DefaultIconButton(
                    imageRes = drawable.ic_arrow_back_24,
                    contentDescription = string.action_navigate_back,
                    onButtonClick = onNavigateBack
                )
            }
        },
        actions = {
            onSearchClick?.let {
                DefaultIconButton(
                    imageRes = drawable.ic_search_24,
                    contentDescription = string.action_search,
                    onButtonClick = onSearchClick
                )
            }
            onDeleteClick?.let {
                DefaultIconButton(
                    imageRes = drawable.ic_delete_24,
                    contentDescription = string.action_delete,
                    onButtonClick = onDeleteClick
                )
            }
            onThemeClick?.let {
                DefaultIconButton(
                    imageRes = drawable.ic_moon_24,
                    contentDescription = string.action_theme,
                    onButtonClick = onThemeClick
                )
            }
            onOptionsClick?.let {
                DefaultIconButton(
                    imageRes = drawable.ic_options_24,
                    contentDescription = string.action_options,
                    onButtonClick = onOptionsClick
                )
            }
        }
    )
}

@Composable
private fun TopBarText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun DefaultIconButton(imageRes: Int, contentDescription: Int, onButtonClick: () -> Unit) {
    IconButton(
        onClick = onButtonClick,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(imageRes),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = stringResource(contentDescription),
        )
    }
}

@Preview
@Composable
private fun MainPreview() {
    ShoppingListTheme { TopBar(title = "Мои списки", onNavigateBack = {}) }
}