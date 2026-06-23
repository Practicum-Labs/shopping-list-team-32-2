package ru.practicum.list.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun ShoppingListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val themedContext = rememberThemedContext(darkTheme)
    val colorScheme = remember(themedContext, darkTheme) {
        colorSchemeFromAttrs(themedContext, darkTheme)
    }

    CompositionLocalProvider(LocalThemedContext provides themedContext) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
