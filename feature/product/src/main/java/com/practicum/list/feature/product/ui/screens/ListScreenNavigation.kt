package com.practicum.list.feature.product.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.practicum.list.core.navigation.ListScreenRoute

private const val DURATION_MILLIS = 300
fun NavGraphBuilder.listScreenNavigation(
    navController: NavController
) {
    composable<ListScreenRoute>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(DURATION_MILLIS)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(DURATION_MILLIS)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(DURATION_MILLIS)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(DURATION_MILLIS)
            )
        }
    ) { entry ->
        val route = entry.toRoute<ListScreenRoute>()
        ListScreen(
            id = route.id,
            onBackTap = { navController.popBackStack() }
        )
    }
}
