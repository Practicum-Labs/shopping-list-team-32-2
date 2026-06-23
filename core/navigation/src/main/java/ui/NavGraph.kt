package ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import domain.ListEntry

@Composable
fun NavGraph(
    startDestination: String = "main",
    lists: List<ListEntry>
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = "main"
        ) { MainScreen(navController, lists) }
        composable(
            route = "onboarding"
        ) {
            OnboardingScreen()
        }
        composable(
            route = "listDetails/{list}",
            arguments = listOf(navArgument("list") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val listJson = navBackStackEntry.arguments?.getString("list")
            val list = ListEntry("Пример экрана списка",0 )//gson.fromJson(listJson, ListEntry::class.java)
            ListScreen(list)
        }
    }
}