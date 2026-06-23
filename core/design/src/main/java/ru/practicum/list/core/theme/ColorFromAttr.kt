package ru.practicum.list.core.theme

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

internal fun resolveColorFromAttr(context: Context, @AttrRes attr: Int): Color {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(attr, typedValue, true)
    return Color(typedValue.data)
}

@Composable
fun colorFromAttr(@AttrRes attr: Int): Color {
    val context = LocalThemedContext.current
    return remember(context, attr) {
        resolveColorFromAttr(context, attr)
    }
}
