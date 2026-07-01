package com.practicum.list.feature.auth.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R

@Composable
fun PasswordRequirementsChecklist(
    requirements: List<Pair<String, Boolean>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        requirements.forEach { (text, isMet) ->
            RequirementRow(text = text, isMet = isMet)
        }
    }
}

@Composable
private fun RequirementRow(
    text: String,
    isMet: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        RequirementIndicator(isMet = isMet)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isMet) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
        )
    }
}

@Composable
private fun RequirementIndicator(isMet: Boolean) {
    if (isMet) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.secondary,
        )
    } else {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape,
                ),
        )
    }
}

@Preview(name = "Light default", showBackground = true)
@Composable
private fun PasswordRequirementsChecklistLightDefaultPreview() {
    ShoppingListTheme(darkTheme = false) {
        PasswordRequirementsChecklist(
            requirements = previewRequirements(),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dark default", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordRequirementsChecklistDarkDefaultPreview() {
    ShoppingListTheme(darkTheme = true) {
        PasswordRequirementsChecklist(
            requirements = previewRequirements(),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Light met", showBackground = true)
@Composable
private fun PasswordRequirementsChecklistLightMetPreview() {
    ShoppingListTheme(darkTheme = false) {
        PasswordRequirementsChecklist(
            requirements = previewRequirements(allMet = true),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dark met", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordRequirementsChecklistDarkMetPreview() {
    ShoppingListTheme(darkTheme = true) {
        PasswordRequirementsChecklist(
            requirements = previewRequirements(allMet = true),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
private fun previewRequirements(allMet: Boolean = false): List<Pair<String, Boolean>> = listOf(
    stringResource(R.string.auth_requirement_min_length) to allMet,
    stringResource(R.string.auth_requirement_has_digit) to allMet,
)
