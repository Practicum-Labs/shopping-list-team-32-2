package com.practicum.list.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.navigation.LoginRoute
import com.practicum.list.core.navigation.MainScreenRoute
import com.practicum.list.core.navigation.RootScreenRoute
import com.practicum.list.core.navigation.anim.defaultEnterTransition
import com.practicum.list.core.navigation.anim.defaultExitTransition
import com.practicum.list.core.navigation.anim.defaultPopEnterTransition
import com.practicum.list.core.navigation.anim.defaultPopExitTransition
import com.practicum.list.presentation.RootEffect
import com.practicum.list.presentation.RootViewModel

fun NavGraphBuilder.rootScreenNavigation(navController: NavController) {
    composable<RootScreenRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition
    ) {
        RootScreenRouteContent(navController = navController)
    }
}

@Composable
private fun RootScreenRouteContent(navController: NavController) {
    val viewModel: RootViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
//            when (effect) {
//                RootEffect.NavigateToLogin -> navController.navigate(LoginRoute) {
//                    popUpTo(RootScreenRoute) {
//                        inclusive = true
//                    }
//                }
//
//                RootEffect.NavigateToMain -> navController.navigate(MainScreenRoute) {
//                    popUpTo(RootScreenRoute) {
//                        inclusive = true
//                    }
//                }
//            }
            navController.navigate(MainScreenRoute)
        }
    }

    Scaffold { paddingValues ->
        RootScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = state,
            onIntent = viewModel::dispatch
        )
    }
}
