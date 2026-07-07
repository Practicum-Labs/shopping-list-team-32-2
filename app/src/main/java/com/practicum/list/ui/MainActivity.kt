package com.practicum.list.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.practicum.list.core.navigation.RootScreenRoute
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.ui.screens.authScreenNavigation
import com.practicum.list.feature.main.ui.screens.mainScreenNavigation
import com.practicum.list.feature.product.ui.screens.listScreenNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                NavHost(
                    navController = navController,
                    startDestination = RootScreenRoute,
                ) {
                    rootScreenNavigation(navController)
                    mainScreenNavigation(navController)
                    listScreenNavigation(navController)
                    authScreenNavigation(navController)
                }
            }
        }
    }
}