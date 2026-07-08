package com.practicum.list.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainScreenRoute

@Serializable
data object RootScreenRoute

@Serializable
data class ListScreenRoute(val listId: Long)

@Serializable
data object LoginRoute

@Serializable
data object RegisterRoute

@Serializable
data object ResetPasswordRoute