package com.practicum.list.feature.product.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.practicum.list.core.navigation.ListScreenRoute

fun NavGraphBuilder.listScreenNavigation(
    navController: NavController
) {
    composable<ListScreenRoute>(
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        }
    ) { entry ->
        val route = entry.toRoute<ListScreenRoute>()
        ListScreen(
            id = route.id,
            onBackTap = { navController.popBackStack() }
        )
    }
}
