package com.practicum.list.core.components.placeholder

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.R

import com.practicum.list.core.theme.ShoppingListTheme

@Composable
fun PlaceholderLayout(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int = R.drawable.ic_shopping_lists_placeholder_324,
    title: String,
    message: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        Image(
            modifier = modifier.fillMaxWidth(),
            painter = painterResource(imageRes),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private const val PLACEHOLDER_TITLE = "У вас пока нет списков"
private const val PLACEHOLDER_MESSAGE = "Нажмите на кнопку + ниже, чтобы создать свой первый список"

@Preview(showSystemUi = true)
@Composable
private fun PlaceholderPreview() {
    ShoppingListTheme {
        PlaceholderLayout(
            modifier = Modifier
                .fillMaxWidth(),
            title = PLACEHOLDER_TITLE,
            message = PLACEHOLDER_MESSAGE,
        )
    }
}