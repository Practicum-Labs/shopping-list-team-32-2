package com.practicum.list.feature.list.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.components.topbar.TopBar
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.core.navigation.anim.defaultEnterTransition
import com.practicum.list.core.navigation.anim.defaultExitTransition
import com.practicum.list.core.navigation.anim.defaultPopEnterTransition
import com.practicum.list.core.navigation.anim.defaultPopExitTransition
import com.practicum.list.feature.list.presentation.ListEffect
import com.practicum.list.feature.list.presentation.ListViewModel
import com.practicum.list.feature.list.R

fun NavGraphBuilder.listScreenNavigation(navController: NavController) {
    composable<ListScreenRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition
    ) {
        ListScreenRouteContent(
            navController = navController
        )
    }
}

@Composable
private fun ListScreenRouteContent(navController: NavController) {
    val viewModel: ListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                ListEffect.NavigateToMain -> navController.popBackStack()
                is ListEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(topBar = {
        TopBar(title = state.listTitle.ifEmpty { stringResource(R.string.list) })
    }) { paddingValues ->
        ListScreen(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onIntent = viewModel::dispatch,
        )
    }
}
