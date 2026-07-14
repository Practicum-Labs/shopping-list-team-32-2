package com.practicum.list.feature.auth.ui.screens.recovery

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.components.topbar.TopBar
import com.practicum.list.core.navigation.ResetPasswordRoute
import com.practicum.list.core.navigation.anim.defaultEnterTransition
import com.practicum.list.core.navigation.anim.defaultExitTransition
import com.practicum.list.core.navigation.anim.defaultPopEnterTransition
import com.practicum.list.core.navigation.anim.defaultPopExitTransition
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.feature.auth.R
import com.practicum.list.feature.auth.presentation.recovery.ResetPasswordEffect
import com.practicum.list.feature.auth.presentation.recovery.ResetPasswordIntent
import com.practicum.list.feature.auth.presentation.recovery.ResetPasswordState
import com.practicum.list.feature.auth.presentation.recovery.ResetPasswordViewModel

fun NavGraphBuilder.resetPasswordScreenNavigation(navController: NavController) {
    composable<ResetPasswordRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition,
    ) {
        ResetPasswordScreenRouteContent(navController = navController)
    }
}

@Composable
private fun ResetPasswordScreenRouteContent(navController: NavController) {
    val viewModel: ResetPasswordViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                ResetPasswordEffect.NavigateBack -> navController.popBackStack()
                is ResetPasswordEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                title = stringResource(R.string.auth_title_reset_password),
                onNavigateBack = { viewModel.dispatch(ResetPasswordIntent.BackClicked) },
            )
        },
    ) { paddingValues ->
        ResetPasswordScreen(
            state = state,
            onIntent = viewModel::dispatch,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun ResetPasswordScreenPreview(
    darkTheme: Boolean,
    state: ResetPasswordState,
) {
    ShoppingListTheme(darkTheme = darkTheme) {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.auth_title_reset_password),
                    onNavigateBack = {},
                )
            },
        ) { paddingValues ->
            ResetPasswordScreen(
                state = state,
                onIntent = {},
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}

@Preview(name = "Light — empty, button disabled", showBackground = true)
@Composable
private fun ResetPasswordEmptyLightPreview() {
    ResetPasswordScreenPreview(
        darkTheme = false,
        state = ResetPasswordState(),
    )
}

@Preview(
    name = "Dark — empty, button disabled",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ResetPasswordEmptyDarkPreview() {
    ResetPasswordScreenPreview(
        darkTheme = true,
        state = ResetPasswordState(),
    )
}

@Preview(name = "Valid email — button enabled", showBackground = true)
@Composable
private fun ResetPasswordValidEmailPreview() {
    ResetPasswordScreenPreview(
        darkTheme = false,
        state = ResetPasswordState(email = "user@example.com"),
    )
}

@Preview(name = "Validation error", showBackground = true)
@Composable
private fun ResetPasswordValidationErrorPreview() {
    ResetPasswordScreenPreview(
        darkTheme = false,
        state = ResetPasswordState(
            email = "not-an-email",
            emailError = stringResource(R.string.auth_error_invalid_email),
        ),
    )
}
