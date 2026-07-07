package com.practicum.list.feature.auth.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.components.buttons.LoginTextButton
import com.practicum.list.core.theme.Dimens
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.presentation.login.LoginIntent
import com.practicum.list.feature.auth.presentation.login.LoginState
import com.practicum.list.feature.auth.ui.components.buttons.PrimaryAuthButton
import com.practicum.list.feature.auth.ui.components.textfields.AuthOutlinedTextField
import com.practicum.list.feature.auth.ui.components.textfields.PasswordTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onIntent: (LoginIntent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = Dimens.ScreenHorizontalPadding,
                top = 80.dp,
                end = Dimens.ScreenHorizontalPadding,
                bottom = 20.dp
            ),
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
            Box(
                modifier = Modifier.padding(top = 48.dp),
            ) {
                AuthOutlinedTextField(
                    value = state.email,
                    onValueChange = {
                        onIntent(LoginIntent.EmailChanged(it))
                    },
                    label = stringResource(R.string.auth_label_email),
                    isError = state.emailError != null,
                    errorText = state.emailError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onIntent(LoginIntent.SubmitEmailClicked(email = state.email))
                            keyboardController?.hide()
                        }
                    ),
                )

                PasswordTextField(
                    modifier = Modifier.offset(y = 84.dp),
                    value = state.password,
                    onValueChange = { onIntent(LoginIntent.PasswordChanged(it)) },
                    label = stringResource(R.string.auth_label_password),
                    isError = state.passwordError != null,
                    errorText = state.passwordError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onIntent(LoginIntent.SubmitPasswordClicked(state.password))
                            keyboardController?.hide()
                        }
                    )
                )
                state.generalError?.let { error ->
                    Text(
                        modifier = Modifier.offset(y = 168.dp),
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PrimaryAuthButton(
                text = stringResource(R.string.auth_button_login),
                onClick = { onIntent(LoginIntent.SubmitClicked) },
                enabled = state.isSubmitEnabled,
                isLoading = state.isLoading,
            )
            LoginTextButton(
                modifier = Modifier,
                onClick = { onIntent(LoginIntent.ResetPasswordClicked) },
                buttonText = stringResource(R.string.forgot_password_question),
                buttonStyle = MaterialTheme.typography.labelMedium
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.no_account_question),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
                LoginTextButton(
                    modifier = Modifier,
                    onClick = { onIntent(LoginIntent.RegisterClicked) },
                    buttonText = stringResource(R.string.auth_button_register)
                )
            }
        }
    }
}

private val loginStateEmpty = LoginState()
private val loginStateFilled = LoginState(email = "hello@gmail.com", password = "zeliboba")

@Preview(name = "Light empty", showSystemUi = true)
@Composable
private fun LoginScreenPreviewEmptyLight() {
    ShoppingListTheme(darkTheme = false) {
        LoginScreen(
            state = loginStateEmpty,
            onIntent = {}
        )
    }
}

@Preview(name = "Light filled", showSystemUi = true)
@Composable
private fun LoginScreenPreviewFilledLight() {
    ShoppingListTheme(darkTheme = false) {
        LoginScreen(
            state = loginStateFilled,
            onIntent = {}
        )
    }
}

@Preview(name = "Dark empty")
@Composable
private fun LoginScreenPreviewEmptyDark() {
    ShoppingListTheme(darkTheme = true) {
        LoginScreen(
            state = loginStateEmpty,
            onIntent = {}
        )
    }
}

@Preview(name = "Dark filled")
@Composable
private fun LoginScreenPreviewFilledDark() {
    ShoppingListTheme(darkTheme = true) {
        LoginScreen(
            state = loginStateFilled,
            onIntent = {}
        )
    }
}
