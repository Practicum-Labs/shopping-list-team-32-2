package com.practicum.list.feature.main.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.core.navigation.MainScreenRoute
import com.practicum.list.feature.main.presentation.MainEffect
import com.practicum.list.feature.main.presentation.MainIntent
import com.practicum.list.feature.main.presentation.MainViewModel

private const val DURATION_MILLIS = 300

fun NavGraphBuilder.mainScreenNavigation(navController: NavController) {
    composable<MainScreenRoute>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(DURATION_MILLIS),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(DURATION_MILLIS),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(DURATION_MILLIS),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(DURATION_MILLIS),
            )
        },
    ) {
        MainScreenRouteContent(navController = navController)
    }
}

@Composable
private fun MainScreenRouteContent(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.dispatch(MainIntent.LoadLists)
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is MainEffect.NavigateToList ->
                    navController.navigate(ListScreenRoute(id = effect.id))

                is MainEffect.ShowDeleteConfirmation -> Unit

                is MainEffect.ShowRenameDialog -> Unit

                is MainEffect.ShowCategoryPicker -> Unit

                is MainEffect.ShowError ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        MainScreen(
            state = state,
            onIntent = viewModel::dispatch,
            modifier = Modifier.padding(paddingValues),
        )
    }
}
