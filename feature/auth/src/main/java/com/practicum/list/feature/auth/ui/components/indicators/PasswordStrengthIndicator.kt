package com.practicum.list.feature.auth.ui.components.indicators

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R

private const val STRENGTH_SEGMENT_COUNT = 3

@Composable
fun PasswordStrengthIndicator(
    level: PasswordStrengthLevel,
    modifier: Modifier = Modifier,
) {
    val filledSegments = when (level) {
        PasswordStrengthLevel.EMPTY -> 0
        PasswordStrengthLevel.WEAK -> 1
        PasswordStrengthLevel.FAIR -> 2
        PasswordStrengthLevel.STRONG -> STRENGTH_SEGMENT_COUNT
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            repeat(STRENGTH_SEGMENT_COUNT) { index ->
                StrengthSegment(isActive = index < filledSegments)
            }
        }
        Text(
            text = strengthLabel(level),
            style = MaterialTheme.typography.bodySmall,
            color = strengthLabelColor(level),
        )
    }
}

@Composable
fun PasswordStrengthIndicator(
    level: Int,
    modifier: Modifier = Modifier,
) {
    PasswordStrengthIndicator(
        level = PasswordStrengthLevel.Companion.fromInt(level),
        modifier = modifier,
    )
}

@Composable
private fun RowScope.StrengthSegment(isActive: Boolean) {
    val color = if (isActive) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.surfaceContainerHighest
    }
    Box(
        modifier = Modifier
            .weight(1f)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color),
    )
}

@Composable
private fun strengthLabel(level: PasswordStrengthLevel): String = stringResource(
    when (level) {
        PasswordStrengthLevel.EMPTY -> R.string.auth_password_strength_empty
        PasswordStrengthLevel.WEAK -> R.string.auth_password_strength_weak
        PasswordStrengthLevel.FAIR -> R.string.auth_password_strength_fair
        PasswordStrengthLevel.STRONG -> R.string.auth_password_strength_strong
    },
)

@Composable
private fun strengthLabelColor(level: PasswordStrengthLevel) = when (level) {
    PasswordStrengthLevel.EMPTY -> MaterialTheme.colorScheme.onSurfaceVariant
    PasswordStrengthLevel.WEAK -> MaterialTheme.colorScheme.error
    PasswordStrengthLevel.FAIR -> MaterialTheme.colorScheme.tertiary
    PasswordStrengthLevel.STRONG -> MaterialTheme.colorScheme.secondary
}

@Preview(name = "Light empty", showBackground = true)
@Composable
private fun PasswordStrengthIndicatorLightEmptyPreview() {
    ShoppingListTheme(darkTheme = false) {
        PasswordStrengthIndicator(
            level = PasswordStrengthLevel.EMPTY,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dark strong", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordStrengthIndicatorDarkStrongPreview() {
    ShoppingListTheme(darkTheme = true) {
        PasswordStrengthIndicator(
            level = PasswordStrengthLevel.STRONG,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Light weak", showBackground = true)
@Composable
private fun PasswordStrengthIndicatorLightWeakPreview() {
    ShoppingListTheme(darkTheme = false) {
        PasswordStrengthIndicator(
            level = PasswordStrengthLevel.WEAK,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dark fair", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordStrengthIndicatorDarkFairPreview() {
    ShoppingListTheme(darkTheme = true) {
        PasswordStrengthIndicator(
            level = 2,
            modifier = Modifier.padding(16.dp),
        )
    }
}
