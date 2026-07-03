package com.practicum.list.feature.auth.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.topbar.TopBar
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.ui.components.buttons.PrimaryAuthButton
import com.practicum.list.feature.auth.ui.components.textfields.AuthOutlinedTextField

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.password_restoration_title),
                onNavigateBack = onNavigateBack,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(R.string.password_restoration_subtitle),
                style = MaterialTheme.typography.bodyLarge,
            )
            AuthOutlinedTextField(
                value = "",
                onValueChange = {},
                label = stringResource(R.string.auth_label_email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                isError = false,
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimaryAuthButton(
                text = "Отправить письмо",
                onClick = {},
                enabled = true,
                modifier = Modifier.padding(bottom = 24.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResetPasswordPreview() {
    ShoppingListTheme {
        ResetPasswordScreen()
    }
}
