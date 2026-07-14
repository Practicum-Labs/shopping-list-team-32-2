package com.practicum.list.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.list.R
import com.practicum.list.core.components.placeholder.PlaceholderLayout

@Composable
fun Onboarding(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(44.dp),
        verticalArrangement = Arrangement.spacedBy(94.dp)
    ) {
        Icon(
            painterResource(R.drawable.ic_onboarding_logo_328),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            tint = MaterialTheme.colorScheme.onSurface
        )

        PlaceholderLayout(
            modifier = Modifier,
            imageRes = R.drawable.ic_onboarding_324,
            title = stringResource(R.string.welcome_to_shopping_list),
            message = stringResource(R.string.welcome_message),
        )
    }
}