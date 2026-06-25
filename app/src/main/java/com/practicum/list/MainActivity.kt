package com.practicum.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.practicum.list.core.common.domain.ListEntry
import com.practicum.list.core.navigation.MainScreenRoute
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.main.ui.screens.mainScreenNavigation
import com.practicum.list.feature.product.ui.screens.listScreenNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val list = listOf(
            ListEntry(1, "??????"),
            ListEntry(2, "!!!!!!"),
            ListEntry(3, "???!!!")
        )

        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                NavHost(
                    navController = navController,
                    startDestination = MainScreenRoute,
                ) {
                    mainScreenNavigation(list, navController)
                    listScreenNavigation(navController)
                }
            }
        }
    }
}
