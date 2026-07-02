package com.practicum.list.feature.auth.ui.components.indicators

enum class PasswordStrengthLevel {
    EMPTY,
    WEAK,
    FAIR,
    STRONG,
    ;

    companion object {
        private const val MAX_STRENGTH_LEVEL = 3

        fun fromInt(level: Int): PasswordStrengthLevel = when (level.coerceIn(0, MAX_STRENGTH_LEVEL)) {
            0 -> EMPTY
            1 -> WEAK
            2 -> FAIR
            else -> STRONG
        }
    }
}