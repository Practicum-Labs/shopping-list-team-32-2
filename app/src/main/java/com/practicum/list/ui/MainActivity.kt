package com.practicum.list.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.practicum.list.core.data.SessionEvents
import com.practicum.list.core.navigation.LoginRoute
import com.practicum.list.core.navigation.RootScreenRoute
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.ui.screens.authScreenNavigation
import com.practicum.list.feature.list.ui.screens.listScreenNavigation
import com.practicum.list.feature.main.ui.screens.mainScreenNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionEvents: SessionEvents

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                SessionExpiredHandler(
                    sessionEvents = sessionEvents,
                    navController = navController,
                )
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

@Composable
private fun SessionExpiredHandler(
    sessionEvents: SessionEvents,
    navController: NavHostController,
) {
    LaunchedEffect(sessionEvents) {
        sessionEvents.sessionExpired.collect {
            navController.navigate(LoginRoute) {
                popUpTo(0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
}
