package com.practicum.list.feature.main.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.common.domain.ListEntry
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.core.navigation.MainScreenRoute

fun NavGraphBuilder.mainScreenNavigation(
    itemsList: List<ListEntry>,
    navController: NavController
) {
    composable<MainScreenRoute>(
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
    ) {
        MainScreen(
            itemsList
        ) {
            navController.navigate(ListScreenRoute(id = it))
        }
    }
}
