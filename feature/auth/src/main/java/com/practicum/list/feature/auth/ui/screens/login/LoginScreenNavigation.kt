package com.practicum.list.feature.auth.ui.screens.login

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
import com.practicum.list.core.navigation.LoginRoute
import com.practicum.list.core.navigation.MainScreenRoute
import com.practicum.list.core.navigation.RegisterRoute
import com.practicum.list.core.navigation.ResetPasswordRoute
import com.practicum.list.core.navigation.anim.defaultEnterTransition
import com.practicum.list.core.navigation.anim.defaultExitTransition
import com.practicum.list.core.navigation.anim.defaultPopEnterTransition
import com.practicum.list.core.navigation.anim.defaultPopExitTransition
import com.practicum.list.feature.auth.presentation.login.LoginEffect
import com.practicum.list.feature.auth.presentation.login.LoginViewModel

fun NavGraphBuilder.loginScreenNavigation(navController: NavController) {
    composable<LoginRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition,
    ) {
        LoginScreenRouteContent(navController = navController)
    }
}

@Composable
private fun LoginScreenRouteContent(navController: NavController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToMain -> navController.navigateToMainClearingAuth()
                LoginEffect.NavigateToRegister -> navController.navigate(RegisterRoute)
                LoginEffect.NavigateToResetPassword -> navController.navigate(ResetPasswordRoute)
                is LoginEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        LoginScreen(
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
