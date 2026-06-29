package com.practicum.list.feature.main.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.common.domain.ShoppingList
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.core.navigation.MainScreenRoute

private const val DURATION_MILLIS = 300

fun NavGraphBuilder.mainScreenNavigation(
    itemsList: List<ShoppingList>,
    navController: NavController
) {
    composable<MainScreenRoute>(
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
    ) {
        MainScreen(
            itemsList,
            onItemTap ={ navController.navigate(ListScreenRoute(id = it)) },
            onAddClick = {}
        )
    }
}
