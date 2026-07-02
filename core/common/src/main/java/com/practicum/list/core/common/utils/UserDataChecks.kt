package com.practicum.list.core.common.utils

import java.util.regex.Pattern

fun String.isEmailValid(): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    val pattern = Pattern.compile(emailRegex)

    return pattern.matcher(this).matches()
}

fun String.isPasswordWeak(minLength: Int) = length < minLength