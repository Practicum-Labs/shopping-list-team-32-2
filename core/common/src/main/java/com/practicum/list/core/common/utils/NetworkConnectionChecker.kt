package com.practicum.list.core.common.utils

fun interface NetworkConnectionChecker {
    fun isConnected(): Boolean
}