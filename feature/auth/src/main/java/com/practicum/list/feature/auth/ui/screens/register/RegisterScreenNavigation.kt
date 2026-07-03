package com.practicum.list.feature.auth.ui.screens.register

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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.components.topbar.TopBar
import com.practicum.list.core.navigation.LoginRoute
import com.practicum.list.core.navigation.MainScreenRoute
import com.practicum.list.core.navigation.RegisterRoute
import com.practicum.list.core.navigation.anim.defaultEnterTransition
import com.practicum.list.core.navigation.anim.defaultExitTransition
import com.practicum.list.core.navigation.anim.defaultPopEnterTransition
import com.practicum.list.core.navigation.anim.defaultPopExitTransition
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.presentation.register.RegisterEffect
import com.practicum.list.feature.auth.presentation.register.RegisterIntent
import com.practicum.list.feature.auth.presentation.register.RegisterViewModel

fun NavGraphBuilder.registerScreenNavigation(navController: NavController) {
    composable<RegisterRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition,
    ) {
        RegisterScreenRouteContent(navController = navController)
    }
}

@Composable
private fun RegisterScreenRouteContent(navController: NavController) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                RegisterEffect.NavigateToMain -> navController.navigateToMainClearingAuth()
                RegisterEffect.NavigateBack -> navController.popBackStack()
                is RegisterEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                title = stringResource(R.string.auth_title_register),
                onNavigateBack = { viewModel.dispatch(RegisterIntent.BackClicked) },
            )
        },
    ) { paddingValues ->
        RegisterScreen(
            state = state,
            onIntent = viewModel::dispatch,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

private fun NavController.navigateToMainClearingAuth() {
    navigate(MainScreenRoute) {
        popUpTo<LoginRoute> { inclusive = true }
    }
}
