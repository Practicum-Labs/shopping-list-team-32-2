package com.practicum.list.feature.auth.domain.validation

import com.practicum.list.core.common.utils.isEmailValid
import com.practicum.list.core.common.utils.isPasswordWeak

object AuthValidation {
    const val MIN_PASSWORD_LENGTH = 7

    fun emailFieldError(email: String): String? = when {
        email.isEmpty() -> null
        !email.isEmailValid() -> INVALID_EMAIL
        else -> null
    }

    fun passwordFieldError(password: String): String? = when {
        password.isEmpty() -> null
        password.isPasswordWeak(MIN_PASSWORD_LENGTH) -> PASSWORD_TOO_SHORT
        else -> null
    }

    fun confirmPasswordError(password: String, confirmPassword: String): String? = when {
        confirmPassword.isEmpty() -> null
        password != confirmPassword -> PASSWORDS_MISMATCH
        else -> null
    }

    fun isLoginFormValid(email: String, password: String): Boolean =
        email.isEmailValid() && !password.isPasswordWeak(MIN_PASSWORD_LENGTH)

    fun isRegisterFormValid(email: String, password: String, confirmPassword: String): Boolean =
        email.isEmailValid() &&
            !password.isPasswordWeak(MIN_PASSWORD_LENGTH) &&
            password == confirmPassword

    fun isRecoveryFormValid(email: String): Boolean = email.isEmailValid()

    fun passwordRequirements(password: String): PasswordRequirements = PasswordRequirements(
        hasMinLength = password.length >= MIN_PASSWORD_LENGTH,
        hasDigit = password.any(Char::isDigit),
        hasUppercase = password.any(Char::isUpperCase),
    )

    fun passwordStrengthLevel(password: String): Int {
        val requirements = passwordRequirements(password)
        return when {
            !requirements.hasMinLength -> STRENGTH_EMPTY
            requirements.hasMinLength && requirements.hasDigit && requirements.hasUppercase -> STRENGTH_STRONG
            requirements.hasMinLength && (requirements.hasDigit || requirements.hasUppercase) -> STRENGTH_FAIR
            else -> STRENGTH_WEAK
        }
    }

    private const val STRENGTH_EMPTY = 0
    private const val STRENGTH_WEAK = 1
    private const val STRENGTH_FAIR = 2
    private const val STRENGTH_STRONG = 3

    private const val INVALID_EMAIL = "Некорректный email"
    private const val PASSWORD_TOO_SHORT = "Минимальная длина — 7 символов"
    private const val PASSWORDS_MISMATCH = "Пароли не совпадают"
}

data class PasswordRequirements(
    val hasMinLength: Boolean,
    val hasDigit: Boolean,
    val hasUppercase: Boolean,
)
