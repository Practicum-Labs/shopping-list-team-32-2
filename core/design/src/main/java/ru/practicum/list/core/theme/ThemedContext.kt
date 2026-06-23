package ru.practicum.list.core.theme

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

internal val LocalThemedContext = staticCompositionLocalOf<Context> {
    error("LocalThemedContext is not provided")
}

@Composable
internal fun rememberThemedContext(darkTheme: Boolean): Context {
    val context = LocalContext.current
    return remember(context, darkTheme) {
        val configuration = Configuration(context.resources.configuration)
        val nightMode = if (darkTheme) {
            Configuration.UI_MODE_NIGHT_YES
        } else {
            Configuration.UI_MODE_NIGHT_NO
        }
        configuration.uiMode =
            configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv() or nightMode
        context.createConfigurationContext(configuration)
    }
}
