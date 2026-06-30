package com.practicum.list.core.components.cards

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.buttons.RoundIconButton
import com.practicum.list.core.components.buttons.ShoppingListActions
import com.practicum.list.core.theme.Dimens.AnimationDuration
import com.practicum.list.core.theme.Dimens.ListItemHeight
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableListItem(
    iconResId: Int,
    text: String,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onCopyClick: () -> Unit
) {
    val actionWidthPx = with(LocalDensity.current) { 160.dp.toPx() }

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.MenuClosed,
        ).apply {
            updateAnchors(
                DraggableAnchors {
                    DragAnchors.MenuClosed at 0f
                    DragAnchors.MenuShown at -actionWidthPx
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(ListItemHeight)
            .background(MaterialTheme.colorScheme.background)
    ) {
        ShoppingListActions(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .graphicsLayer {
                    alpha = (-state.requireOffset() / actionWidthPx).coerceIn(0f, 1f)
                },
            onDeleteClick = onDeleteClick,
            onCopyClick = onCopyClick,
            onEditClick = onEditClick
        )
        ShoppingListCell(
            text = text,
            iconResId = iconResId,
            modifier = Modifier,
            onClick = onClick,
            state = state
        )
    }
}

@Composable
fun ShoppingListCell(
    text: String,
    iconResId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    state: AnchoredDraggableState<DragAnchors>,
) {
    val flingBehavior = AnchoredDraggableDefaults.flingBehavior(
        state = state,
        animationSpec = tween(durationMillis = AnimationDuration, easing = FastOutSlowInEasing),
    )

    ListItem(
        modifier = modifier
            .offset { IntOffset(state.requireOffset().roundToInt(), 0) }
            .anchoredDraggable(state, Orientation.Horizontal, flingBehavior = flingBehavior)
            .padding(start = 16.dp)
            .height(ListItemHeight)
            .width(380.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
            ),
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RoundIconButton(
                    resId = iconResId,
                    onClick = onClick,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    enabled = false
                )
                Text(
                    maxLines = 1,
                    text = text,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        },
        overlineContent = {},
        colors = ListItemColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            headlineColor = MaterialTheme.colorScheme.onSurface,
            leadingIconColor = MaterialTheme.colorScheme.background,
            overlineColor = MaterialTheme.colorScheme.background,
            supportingTextColor = MaterialTheme.colorScheme.background,
            trailingIconColor = MaterialTheme.colorScheme.background,
            disabledHeadlineColor = MaterialTheme.colorScheme.background,
            disabledLeadingIconColor = MaterialTheme.colorScheme.background,
            disabledTrailingIconColor = MaterialTheme.colorScheme.background
        ),
    )

}