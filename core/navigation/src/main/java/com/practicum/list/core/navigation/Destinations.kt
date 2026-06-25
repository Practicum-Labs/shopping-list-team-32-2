package com.practicum.list.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainScreenRoute

@Serializable
data class ListScreenRoute(val id: Long)
