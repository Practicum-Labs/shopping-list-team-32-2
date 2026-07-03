package com.practicum.list.feature.auth.ui.screens.login

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.presentation.login.LoginIntent
import com.practicum.list.feature.auth.presentation.login.LoginState
import com.practicum.list.feature.auth.ui.components.buttons.PrimaryAuthButton
import com.practicum.list.feature.auth.ui.components.textfields.AuthOutlinedTextField
import com.practicum.list.feature.auth.ui.components.textfields.PasswordTextField

@Composable
fun LoginScreen(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AuthOutlinedTextField(
            value = state.email,
            onValueChange = { onIntent(LoginIntent.EmailChanged(it)) },
            label = stringResource(R.string.auth_label_email),
            isError = state.emailError != null,
            errorText = state.emailError,
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = state.password,
            onValueChange = { onIntent(LoginIntent.PasswordChanged(it)) },
            label = stringResource(R.string.auth_label_password),
            isError = state.passwordError != null,
            errorText = state.passwordError,
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
            text = stringResource(R.string.auth_button_login),
            onClick = { onIntent(LoginIntent.SubmitClicked) },
            enabled = state.isSubmitEnabled,
            isLoading = state.isLoading,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onIntent(LoginIntent.RegisterClicked) }) {
            Text(text = stringResource(R.string.auth_button_register))
        }

        TextButton(onClick = { onIntent(LoginIntent.ResetPasswordClicked) }) {
            Text(text = stringResource(R.string.auth_button_forgot_password))
        }
    }
}
