package com.practicum.list.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainScreenRoute

@Serializable
data class ListScreenRoute(val id: Long)

@Serializable
data object LoginRoute

@Serializable
data object RegisterRoute

@Serializable
data object ResetPasswordRoute