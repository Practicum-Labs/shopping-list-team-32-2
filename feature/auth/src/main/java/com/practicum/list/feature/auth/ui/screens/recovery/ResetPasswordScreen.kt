package com.practicum.list.feature.auth.ui.screens.recovery

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
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.topbar.TopBar
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.presentation.recovery.ResetPasswordIntent
import com.practicum.list.feature.auth.presentation.recovery.ResetPasswordState
import com.practicum.list.feature.auth.ui.components.buttons.PrimaryAuthButton
import com.practicum.list.feature.auth.ui.components.textfields.AuthOutlinedTextField

@Composable
fun ResetPasswordScreen(
    state: ResetPasswordState,
    onIntent: (ResetPasswordIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.password_restoration_title),
                onNavigateBack = { onIntent(ResetPasswordIntent.BackClicked) },
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
                onValueChange = { onIntent(ResetPasswordIntent.EmailChanged(it)) },
                label = stringResource(R.string.auth_label_email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                isError = state.emailError != null,
                errorText = state.emailError,
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimaryAuthButton(
                text = stringResource(R.string.auth_button_reset_password),
                onClick = { onIntent(ResetPasswordIntent.SubmitClicked) },
                enabled = state.isSubmitEnabled,
                isLoading = state.isLoading,
            )
            PrimaryAuthButton(
                text = stringResource(R.string.auth_link_back_to_login),
                onClick = { onIntent(ResetPasswordIntent.ReturnToLoginClicked) },
                enabled = true,
                modifier = Modifier.padding(bottom = 24.dp),
            )
        }
    }
}
