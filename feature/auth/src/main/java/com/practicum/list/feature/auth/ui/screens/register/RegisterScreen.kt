package com.practicum.list.feature.auth.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.presentation.login.LoginIntent
import com.practicum.list.feature.auth.presentation.register.RegisterIntent
import com.practicum.list.feature.auth.presentation.register.RegisterState
import com.practicum.list.feature.auth.ui.components.buttons.PrimaryAuthButton
import com.practicum.list.feature.auth.ui.components.checklists.PasswordRequirementsChecklist
import com.practicum.list.feature.auth.ui.components.indicators.PasswordStrengthIndicator
import com.practicum.list.feature.auth.ui.components.textfields.AuthOutlinedTextField
import com.practicum.list.feature.auth.ui.components.textfields.PasswordTextField

@Composable
fun RegisterScreen(
    state: RegisterState,
    onIntent: (RegisterIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        AuthOutlinedTextField(
            value = state.email,
            onValueChange = { onIntent(RegisterIntent.EmailChanged(it)) },
            label = stringResource(R.string.auth_label_email),
            isError = state.emailError != null,
            errorText = state.emailError,
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = state.password,
            onValueChange = { onIntent(RegisterIntent.PasswordChanged(it)) },
            label = stringResource(R.string.auth_label_password),
            isError = state.passwordError != null,
            errorText = state.passwordError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onIntent(RegisterIntent.SubmitClicked)
                    keyboardController?.hide()
                }
            )
        )

        if (state.password.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            PasswordStrengthIndicator(level = state.passwordStrengthLevel)
            Spacer(modifier = Modifier.height(12.dp))
            PasswordRequirementsChecklist(
                requirements = listOf(
                    stringResource(R.string.auth_requirement_min_length) to state.passwordRequirements.hasMinLength,
                    stringResource(R.string.auth_requirement_has_digit) to state.passwordRequirements.hasDigit,
                ),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = state.confirmPassword,
            onValueChange = { onIntent(RegisterIntent.ConfirmPasswordChanged(it)) },
            label = stringResource(R.string.auth_label_confirm_password),
            isError = state.confirmPasswordError != null,
            errorText = state.confirmPasswordError,
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
            text = stringResource(R.string.auth_button_register),
            onClick = { onIntent(RegisterIntent.SubmitClicked) },
            enabled = state.isSubmitEnabled,
            isLoading = state.isLoading,
        )
    }
}
