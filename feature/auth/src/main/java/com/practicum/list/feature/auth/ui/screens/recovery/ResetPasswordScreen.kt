package com.practicum.list.feature.auth.ui.screens.recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        if (state.isEmailSent) {
            Text(
                text = stringResource(R.string.auth_reset_success_title),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.auth_reset_success_message),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(onClick = { onIntent(ResetPasswordIntent.ReturnToLoginClicked) }) {
                Text(text = stringResource(R.string.auth_link_back_to_login))
            }
        } else {
            AuthOutlinedTextField(
                value = state.email,
                onValueChange = { onIntent(ResetPasswordIntent.EmailChanged(it)) },
                label = stringResource(R.string.auth_label_email),
                isError = state.emailError != null,
                errorText = state.emailError,
            )

            state.generalError?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryAuthButton(
                text = stringResource(R.string.auth_button_reset_password),
                onClick = { onIntent(ResetPasswordIntent.SubmitClicked) },
                enabled = state.isSubmitEnabled,
                isLoading = state.isLoading,
            )
        }
    }
}
