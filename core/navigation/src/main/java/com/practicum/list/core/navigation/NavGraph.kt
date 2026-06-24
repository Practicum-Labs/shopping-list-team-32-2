package com.practicum.list.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.lang.reflect.Modifier

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainScreen: @Composable () -> Unit,
    listScreen: Composable () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = "main"
        ) { mainScreen() }
        composable(
            route = "listDetails/{list}",
            arguments = listOf(navArgument("list") { type = NavType.StringType })
        ) {
//            navBackStackEntry ->
//            val listJson = navBackStackEntry.arguments?.getString("list")
//            val list = ListEntry("Пример экрана списка",0 )//gson.fromJson(listJson, ListEntry::class.java)
            listScreen()
        }
    }
}