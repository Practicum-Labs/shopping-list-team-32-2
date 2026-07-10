package com.practicum.list.feature.list.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.cards.DragAnchors
import com.practicum.list.core.theme.Dimens.AnimationDuration
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.list.R
import kotlin.math.roundToInt

private const val PREVIEW_PRODUCT_NAME = "Яблоки"
private const val PREVIEW_QUANTITY_LABEL = "1 кг"
private const val PREVIEW_LONG_PRODUCT_NAME =
    "Очень длинное название товара которое не помещается в две строки списка покупок"

private val ProductSwipeActionsWidth = 96.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListItem(
    name: String,
    quantityLabel: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onQuantityClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val actionWidthPx = with(density) { ProductSwipeActionsWidth.toPx() }
    val velocityThresholdPx = with(density) { 100.dp.toPx() }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()

    val dragState = remember(actionWidthPx) {
        AnchoredDraggableState(
            initialValue = DragAnchors.MenuClosed,
            anchors = DraggableAnchors {
                DragAnchors.MenuClosed at 0f
                DragAnchors.MenuShown at -actionWidthPx
            },
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { velocityThresholdPx },
            snapAnimationSpec = tween(durationMillis = AnimationDuration, easing = FastOutSlowInEasing),
            decayAnimationSpec = decayAnimationSpec,
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        ProductListActions(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .graphicsLayer {
                    alpha = (-dragState.requireOffset() / actionWidthPx).coerceIn(0f, 1f)
                },
            onEditClick = onEdit,
            onDeleteClick = onDelete,
        )
        ProductListItemContent(
            name = name,
            quantityLabel = quantityLabel,
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
            onQuantityClick = onQuantityClick,
            modifier = Modifier
                .offset { IntOffset(dragState.requireOffset().roundToInt(), 0) }
                .anchoredDraggable(dragState, Orientation.Horizontal)
                .background(MaterialTheme.colorScheme.surface),
        )
    }
}

@Composable
private fun ProductListItemContent(
    name: String,
    quantityLabel: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onQuantityClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val nameColor = if (isChecked) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val nameDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
    val checkboxDescription = stringResource(R.string.product_checkbox_content_description)
    val quantityDescription = stringResource(R.string.product_quantity_content_description)

    Column(modifier = modifier.fillMaxWidth()) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            leadingContent = {
                ProductRoundCheckbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier.semantics { contentDescription = checkboxDescription },
                )
            },
            headlineContent = {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = nameColor,
                        textDecoration = nameDecoration,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            supportingContent = {
                Text(
                    text = quantityLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .clickable(onClick = onQuantityClick)
                        .semantics { contentDescription = quantityDescription },
                )
            },
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Preview(name = "Light default", showBackground = true)
@Composable
private fun ProductListItemLightDefaultPreview() {
    ShoppingListTheme(darkTheme = false) {
        ProductListItem(
            name = PREVIEW_PRODUCT_NAME,
            quantityLabel = PREVIEW_QUANTITY_LABEL,
            isChecked = false,
            onCheckedChange = {},
            onQuantityClick = {},
            onEdit = {},
            onDelete = {},
        )
    }
}

@Preview(name = "Light checked", showBackground = true)
@Composable
private fun ProductListItemLightCheckedPreview() {
    ShoppingListTheme(darkTheme = false) {
        ProductListItem(
            name = PREVIEW_PRODUCT_NAME,
            quantityLabel = PREVIEW_QUANTITY_LABEL,
            isChecked = true,
            onCheckedChange = {},
            onQuantityClick = {},
            onEdit = {},
            onDelete = {},
        )
    }
}

@Preview(name = "Dark default", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProductListItemDarkDefaultPreview() {
    ShoppingListTheme(darkTheme = true) {
        ProductListItem(
            name = PREVIEW_PRODUCT_NAME,
            quantityLabel = PREVIEW_QUANTITY_LABEL,
            isChecked = false,
            onCheckedChange = {},
            onQuantityClick = {},
            onEdit = {},
            onDelete = {},
        )
    }
}

@Preview(name = "Long product name", showBackground = true)
@Composable
private fun ProductListItemLongNamePreview() {
    ShoppingListTheme(darkTheme = false) {
        ProductListItem(
            name = PREVIEW_LONG_PRODUCT_NAME,
            quantityLabel = "10 кг",
            isChecked = false,
            onCheckedChange = {},
            onQuantityClick = {},
            onEdit = {},
            onDelete = {},
        )
    }
}
