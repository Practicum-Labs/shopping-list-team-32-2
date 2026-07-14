package com.practicum.list.feature.list.ui.components.textedit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CommonTextFieldLabel(resId: Int) {
    Text(stringResource(resId), overflow = TextOverflow.Ellipsis, maxLines = 1)
}