package com.practicum.list.feature.auth.ui.screens.recovery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.Dimens.LoginVerticalPadding
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
            .padding(horizontal = 16.dp),
    ) {
        if (state.isEmailSent) {
            Text(
                modifier = Modifier.padding(top = LoginVerticalPadding),
                text = stringResource(R.string.auth_reset_success_title),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(top = LoginVerticalPadding),
                text = stringResource(R.string.auth_reset_success_message),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimaryAuthButton(
                text = stringResource(R.string.auth_link_back_to_login),
                onClick = { onIntent(ResetPasswordIntent.ReturnToLoginClicked) },
                enabled = true,
                isLoading = state.isLoading,
                modifier = Modifier.padding(bottom = LoginVerticalPadding),
            )
        } else {
            Text(
                modifier = Modifier.padding(top = LoginVerticalPadding),
                text = stringResource(R.string.password_restoration_subtitle),
                style = MaterialTheme.typography.bodyLarge,
            )
            AuthOutlinedTextField(
                value = state.email,
                onValueChange = { onIntent(ResetPasswordIntent.EmailChanged(it)) },
                label = stringResource(R.string.auth_label_email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LoginVerticalPadding),
                isError = state.emailError != null,
                errorText = state.emailError,
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimaryAuthButton(
                text = stringResource(R.string.auth_button_reset_password),
                onClick = { onIntent(ResetPasswordIntent.SubmitClicked) },
                enabled = state.isSubmitEnabled,
                isLoading = state.isLoading,
                modifier = Modifier.padding(bottom = LoginVerticalPadding),
            )
        }
    }
}
