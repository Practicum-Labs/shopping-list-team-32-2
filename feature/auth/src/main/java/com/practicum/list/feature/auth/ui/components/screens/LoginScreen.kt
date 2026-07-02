package com.practicum.list.feature.auth.ui.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.buttons.LoginTextButton
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.ui.components.buttons.PrimaryAuthButton
import com.practicum.list.feature.auth.ui.components.textfields.AuthOutlinedTextField
import com.practicum.list.feature.auth.ui.components.textfields.PasswordTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLogin: () -> Unit = {},
    onNavigateRecovery: () -> Unit = {},
    onNavigateRegistration: () -> Unit = {}
//    state: LoginUIState,
//    onIntent: (LoginIntent) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 52.dp, end = 16.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.align(Alignment.Start)) {
            Text(

                text = stringResource(R.string.headline_welcome),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(R.string.login_required_message),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
            AuthOutlinedTextField(
                modifier = Modifier.padding(top = 48.dp),
                value = "vanya123",
                onValueChange = onEmailChange,
                label = "Email",
                isError = false,
            )
            PasswordTextField(
                modifier = Modifier.padding(top = 20.dp),
                value = "123",
                onValueChange = onPasswordChange,
                label = "Пароль",
                isError = false,
                errorText = ""
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PrimaryAuthButton(
                text = stringResource(R.string.auth_button_login),
                onClick = onLogin,
                enabled = true,
                isLoading = false
            )
            LoginTextButton(
                modifier = Modifier,
                onClick = onNavigateRecovery,
                buttonText = stringResource(R.string.forgot_password_question)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.no_account_question),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
                LoginTextButton(
                    modifier = Modifier,
                    onClick = onNavigateRegistration,
                    buttonText = stringResource(R.string.auth_button_register)
                )
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    ShoppingListTheme {
        LoginScreen()
    }
}

