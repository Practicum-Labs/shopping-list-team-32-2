package com.practicum.list.feature.list.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

private val CheckboxSize = 24.dp
private val CheckboxBorderWidth = 2.dp
private val CheckmarkSize = 12.dp

@Composable
fun ProductRoundCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = MaterialTheme.colorScheme.onSurfaceVariant
    val checkedColor = MaterialTheme.colorScheme.primary
    val checkmarkColor = MaterialTheme.colorScheme.onPrimary

    Box(
        modifier = modifier
            .size(CheckboxSize)
            .clip(CircleShape)
            .then(
                if (checked) {
                    Modifier.background(checkedColor)
                } else {
                    Modifier.border(CheckboxBorderWidth, borderColor, CircleShape)
                },
            )
            .toggleable(
                value = checked,
                role = Role.Checkbox,
                onValueChange = onCheckedChange,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            CheckmarkIcon(tint = checkmarkColor)
        }
    }
}

@Composable
private fun CheckmarkIcon(
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.size(CheckmarkSize)) {
        val path = Path().apply {
            moveTo(size.width * 0.18f, size.height * 0.52f)
            lineTo(size.width * 0.42f, size.height * 0.76f)
            lineTo(size.width * 0.82f, size.height * 0.28f)
        }
        drawPath(
            path = path,
            color = tint,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
}
