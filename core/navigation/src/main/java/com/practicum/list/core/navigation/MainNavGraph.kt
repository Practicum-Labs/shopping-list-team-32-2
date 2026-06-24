package com.practicum.list.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavGraph(
    navController: NavHostController,
    mainScreenNavigation: (navController: NavHostController) -> NavGraphBuilder,
    listScreenNavigation: (navController: NavHostController) ->  NavGraphBuilder
) {
    NavHost(navController = navController, startDestination = "main") {
        mainScreenNavigation(navController)
        listScreenNavigation(navController)
    }
}