package com.practicum.list.feature.list.ui.components.quantifier

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.list.R

private const val MIN_COUNT = 1
private const val INCREMENT = 1

@Composable
fun RoundQuantifier(
    modifier: Modifier = Modifier,
    count: Float,
    onCountChange: (Float) -> Unit,
) {
    val buttonColors = IconButtonDefaults.iconButtonColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onSurface
    )

    Row(modifier = modifier.height(48.dp)) {
        IconButton(
            onClick = {
                onCountChange(count - INCREMENT)
            },
            enabled = count > MIN_COUNT,
            colors = buttonColors,
        ) {
            Icon(
                painterResource(R.drawable.ic_minus_24),
                contentDescription = stringResource(R.string.minus)
            )
        }

        IconButton(
            onClick = {
                onCountChange(count + INCREMENT)
            },
            colors = buttonColors,
        ) {
            Icon(
                painterResource(R.drawable.ic_plus_24),
                contentDescription = stringResource(R.string.plus)
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun QuantifierPreview() {
    ShoppingListTheme {
        RoundQuantifier(
            count = 4f,
            onCountChange = {}
        )
    }
}
