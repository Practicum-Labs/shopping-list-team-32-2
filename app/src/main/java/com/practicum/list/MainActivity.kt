package com.practicum.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.practicum.list.core.data.SessionEvents
import com.practicum.list.core.navigation.MainScreenRoute
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.ui.screens.authScreenNavigation
import com.practicum.list.feature.main.ui.screens.mainScreenNavigation
import com.practicum.list.feature.product.ui.screens.listScreenNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var sessionEvents: SessionEvents

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            sessionEvents.sessionExpired.collect {
            // navController.navigate(LoginRoute) - реализовать когда появится логин рут
            // или вынести в RootViewModel
            }
        }

        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                NavHost(
                    navController = navController,
                    startDestination = MainScreenRoute,
                ) {
                    mainScreenNavigation(navController)
                    listScreenNavigation(navController)
                    authScreenNavigation(navController)
                }
            }
        }
    }
}
