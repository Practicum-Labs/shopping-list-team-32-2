package ru.practicum.list.core.theme

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

internal fun colorSchemeFromAttrs(context: Context, darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        darkColorSchemeFromAttrs(context)
    } else {
        lightColorSchemeFromAttrs(context)
    }
}

private fun lightColorSchemeFromAttrs(context: Context): ColorScheme = lightColorScheme(
    primary = resolveColorFromAttr(context, R.attr.colorAppPrimary),
    primaryContainer = resolveColorFromAttr(context, R.attr.colorAppPrimaryContainer),
    onPrimaryContainer = resolveColorFromAttr(context, R.attr.colorAppOnPrimaryContainer),
    secondary = resolveColorFromAttr(context, R.attr.colorAppSecondary),
    secondaryContainer = resolveColorFromAttr(context, R.attr.colorAppSecondaryContainer),
    onSecondaryContainer = resolveColorFromAttr(context, R.attr.colorAppOnSecondaryContainer),
    tertiary = resolveColorFromAttr(context, R.attr.colorAppTertiary),
    tertiaryContainer = resolveColorFromAttr(context, R.attr.colorAppTertiaryContainer),
    onTertiaryContainer = resolveColorFromAttr(context, R.attr.colorAppOnTertiaryContainer),
    background = resolveColorFromAttr(context, R.attr.colorAppSurface),
    onBackground = resolveColorFromAttr(context, R.attr.colorAppOnSurface),
    surface = resolveColorFromAttr(context, R.attr.colorAppSurface),
    onSurface = resolveColorFromAttr(context, R.attr.colorAppOnSurface),
    surfaceVariant = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerHigh),
    onSurfaceVariant = resolveColorFromAttr(context, R.attr.colorAppOnSurfaceVariant),
    surfaceContainer = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainer),
    surfaceContainerHigh = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerHigh),
    surfaceContainerLow = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerLow),
    surfaceContainerLowest = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerLowest),
    surfaceContainerHighest = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerHighest),
    inverseOnSurface = resolveColorFromAttr(context, R.attr.colorAppInverseOnSurface),
    outline = resolveColorFromAttr(context, R.attr.colorAppOutline),
    outlineVariant = resolveColorFromAttr(context, R.attr.colorAppOutlineVariant),
)

private fun darkColorSchemeFromAttrs(context: Context): ColorScheme = darkColorScheme(
    primary = resolveColorFromAttr(context, R.attr.colorAppPrimary),
    primaryContainer = resolveColorFromAttr(context, R.attr.colorAppPrimaryContainer),
    onPrimaryContainer = resolveColorFromAttr(context, R.attr.colorAppOnPrimaryContainer),
    secondary = resolveColorFromAttr(context, R.attr.colorAppSecondary),
    secondaryContainer = resolveColorFromAttr(context, R.attr.colorAppSecondaryContainer),
    onSecondaryContainer = resolveColorFromAttr(context, R.attr.colorAppOnSecondaryContainer),
    tertiary = resolveColorFromAttr(context, R.attr.colorAppTertiary),
    tertiaryContainer = resolveColorFromAttr(context, R.attr.colorAppTertiaryContainer),
    onTertiaryContainer = resolveColorFromAttr(context, R.attr.colorAppOnTertiaryContainer),
    background = resolveColorFromAttr(context, R.attr.colorAppSurface),
    onBackground = resolveColorFromAttr(context, R.attr.colorAppOnSurface),
    surface = resolveColorFromAttr(context, R.attr.colorAppSurface),
    onSurface = resolveColorFromAttr(context, R.attr.colorAppOnSurface),
    surfaceVariant = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerHigh),
    onSurfaceVariant = resolveColorFromAttr(context, R.attr.colorAppOnSurfaceVariant),
    surfaceContainer = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainer),
    surfaceContainerHigh = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerHigh),
    surfaceContainerLow = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerLow),
    surfaceContainerLowest = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerLowest),
    surfaceContainerHighest = resolveColorFromAttr(context, R.attr.colorAppSurfaceContainerHighest),
    inverseOnSurface = resolveColorFromAttr(context, R.attr.colorAppInverseOnSurface),
    outline = resolveColorFromAttr(context, R.attr.colorAppOutline),
    outlineVariant = resolveColorFromAttr(context, R.attr.colorAppOutlineVariant),
)
