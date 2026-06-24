package com.practicum.list.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavGraph(
    navController: NavHostController,
    mainScreen: @Composable (
        onItemTap: () -> Unit
    ) -> Unit,
    listScreen: @Composable (
        onBackTap: () -> Unit
    ) -> Unit,
) {
    NavHost(navController = navController, startDestination = "main") {
        composable(
            route = "main"
        ) {
            mainScreen { navController.navigate("item") }
        }
        composable(
            route = "listDetails/{list}",
            arguments = listOf(navArgument("list") { type = NavType.StringType })
        ) {
//            navBackStackEntry ->
//            val listJson = navBackStackEntry.arguments?.getString("list")
//            val list = ListEntry("Пример экрана списка",0 )//gson.fromJson(listJson, ListEntry::class.java)
            listScreen { navController.navigate("main") }
        }
    }
}