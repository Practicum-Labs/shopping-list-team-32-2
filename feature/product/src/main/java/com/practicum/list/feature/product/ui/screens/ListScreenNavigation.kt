package com.practicum.list.feature.product.ui.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.core.navigation.anim.defaultEnterTransition
import com.practicum.list.core.navigation.anim.defaultExitTransition
import com.practicum.list.core.navigation.anim.defaultPopEnterTransition
import com.practicum.list.core.navigation.anim.defaultPopExitTransition

fun NavGraphBuilder.listScreenNavigation(
    navController: NavController
) {
    composable<ListScreenRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition
    ) { entry ->
        val route = entry.toRoute<ListScreenRoute>()
        ListScreen(
            id = route.id,
            onBackTap = { navController.popBackStack() }
        )
    }
}
