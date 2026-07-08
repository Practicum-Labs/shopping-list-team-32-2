package com.practicum.list.feature.auth.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.domain.validation.PasswordRequirements
import com.practicum.list.feature.auth.presentation.register.RegisterIntent
import com.practicum.list.feature.auth.presentation.register.RegisterState
import com.practicum.list.feature.auth.ui.components.buttons.PrimaryAuthButton
import com.practicum.list.feature.auth.ui.components.checklists.PasswordRequirementsChecklist
import com.practicum.list.feature.auth.ui.components.indicators.PasswordStrengthIndicator
import com.practicum.list.feature.auth.ui.components.textfields.AuthOutlinedTextField
import com.practicum.list.feature.auth.ui.components.textfields.PasswordTextField

const val PREVIEW_EMAIL_INPUT = "ivan@mail.ru"

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
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            AuthOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.email,
                onValueChange = { onIntent(RegisterIntent.EmailChanged(it)) },
                label = stringResource(R.string.auth_label_email),
                isError = state.emailError != null,
                errorText = state.emailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onIntent(RegisterIntent.SubmitEmailClicked(email = state.email))
                        keyboardController?.hide()
                    }
                )
            )

            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 90.dp),
                value = state.password,
                onValueChange = { onIntent(RegisterIntent.PasswordChanged(it)) },
                label = stringResource(R.string.auth_label_password),
                isError = state.passwordError != null,
                errorText = state.passwordError
            )

            if (state.password.isNotEmpty()) {
                PasswordStrengthIndicator(
                    modifier = Modifier.offset(y = 180.dp),
                    level = state.passwordStrengthLevel
                )

                // Для детекта, он ругается на длинные строки кода
                val minLengthText = stringResource(R.string.auth_requirement_min_length)
                val hasDigitText = stringResource(R.string.auth_requirement_has_digit)
                val hasUppercaseText = stringResource(R.string.auth_requirement_has_uppercase)

                PasswordRequirementsChecklist(
                    modifier = Modifier.offset(y = 220.dp),
                    requirements = listOf(
                        minLengthText to state.passwordRequirements.hasMinLength,
                        hasDigitText to state.passwordRequirements.hasDigit,
                        hasUppercaseText to state.passwordRequirements.hasUppercase
                    )
                )
            }

            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 310.dp),
                value = state.confirmPassword,
                onValueChange = { onIntent(RegisterIntent.ConfirmPasswordChanged(it)) },
                label = stringResource(R.string.auth_label_confirm_password),
                isError = state.confirmPasswordError != null,
                errorText = state.confirmPasswordError
            )
        }

        PrimaryAuthButton(
            modifier = Modifier.padding(bottom = 24.dp),
            text = stringResource(R.string.auth_button_register),
            onClick = { onIntent(RegisterIntent.SubmitClicked) },
            enabled = state.isSubmitEnabled,
            isLoading = state.isLoading
        )
    }
}

@Preview(name = "Empty fields - Light", showBackground = true)
@Composable
private fun PreviewRegisterScreenEmptyLight() {
    ShoppingListTheme(darkTheme = false) {
        RegisterScreen(
            state = RegisterState(
                email = "",
                password = "",
                confirmPassword = "",
                emailError = null,
                passwordError = null,
                confirmPasswordError = null,
                generalError = null,
                isLoading = false,
                passwordStrengthLevel = 0,
                passwordRequirements = PasswordRequirements(
                    hasMinLength = false,
                    hasDigit = false,
                    hasUppercase = false
                ),
            ),
            onIntent = {}
        )
    }
}

@Preview(name = "Empty fields - Dark", showBackground = true)
@Composable
private fun PreviewRegisterScreenEmptyDark() {
    ShoppingListTheme(darkTheme = true) {
        RegisterScreen(
            state = RegisterState(
                email = "",
                password = "",
                confirmPassword = "",
                emailError = null,
                passwordError = null,
                confirmPasswordError = null,
                generalError = null,
                isLoading = false,
                passwordStrengthLevel = 0,
                passwordRequirements = PasswordRequirements(
                    hasMinLength = false,
                    hasDigit = false,
                    hasUppercase = false
                ),
            ),
            onIntent = {}
        )
    }
}

@Preview(name = "Weak password strength 1 - Light", showBackground = true)
@Composable
private fun PreviewRegisterScreenWeakPassword() {
    ShoppingListTheme(darkTheme = false) {
        RegisterScreen(
            state = RegisterState(
                email = PREVIEW_EMAIL_INPUT,
                password = "vanyavanya",
                confirmPassword = "",
                emailError = null,
                passwordError = null,
                confirmPasswordError = null,
                generalError = null,
                isLoading = false,
                passwordStrengthLevel = 1,
                passwordRequirements = PasswordRequirements(
                    hasMinLength = true,
                    hasDigit = false,
                    hasUppercase = false
                ),
            ),
            onIntent = {}
        )
    }
}

@Preview(name = "Password strength 2 mismatch - Light", showBackground = true)
@Composable
private fun PreviewRegisterScreenPasswordMismatch() {
    ShoppingListTheme(darkTheme = false) {
        RegisterScreen(
            state = RegisterState(
                email = PREVIEW_EMAIL_INPUT,
                password = "vanya123",
                confirmPassword = "vanya12",
                emailError = null,
                passwordError = null,
                confirmPasswordError = "Пароли не совпадают",
                generalError = null,
                isLoading = false,
                passwordStrengthLevel = 2,
                passwordRequirements = PasswordRequirements(
                    hasMinLength = true,
                    hasDigit = true,
                    hasUppercase = false
                ),
            ),
            onIntent = {}
        )
    }
}

@Preview(name = "Password strength 3, matching passwords - Light", showBackground = true)
@Composable
private fun PreviewRegisterScreenAllValid() {
    ShoppingListTheme(darkTheme = false) {
        RegisterScreen(
            state = RegisterState(
                email = PREVIEW_EMAIL_INPUT,
                password = "Vanya123",
                confirmPassword = "Vanya123",
                emailError = null,
                passwordError = null,
                confirmPasswordError = null,
                generalError = null,
                isLoading = false,
                passwordStrengthLevel = 3,
                passwordRequirements = PasswordRequirements(
                    hasMinLength = true,
                    hasDigit = true,
                    hasUppercase = true
                ),
            ),
            onIntent = {}
        )
    }
}