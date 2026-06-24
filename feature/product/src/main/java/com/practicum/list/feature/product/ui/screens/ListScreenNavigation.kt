package com.practicum.list.feature.product.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

fun NavGraphBuilder.listScreenNavigation(
    navController: NavController
) {
    composable(
        route = "listDetails/{list}",
        enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(300)
            )
        },
        exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(300)
            )
        },
        popEnterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(300)
            )
        },
        popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(300)
            )
        },
        arguments = listOf(navArgument("list") { type = NavType.StringType })
    ) {
//            navBackStackEntry ->
//            val listJson = navBackStackEntry.arguments?.getString("list")
//            val list = ListEntry("Пример экрана списка",0 )//gson.fromJson(listJson, ListEntry::class.java)
        listScreen { navController.navigate("main") }
    }
}