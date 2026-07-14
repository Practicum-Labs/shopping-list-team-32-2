package com.practicum.list.feature.main.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.components.topbar.TopBar
import com.practicum.list.core.navigation.ListScreenRoute
import com.practicum.list.core.navigation.MainScreenRoute
import com.practicum.list.core.navigation.anim.defaultEnterTransition
import com.practicum.list.core.navigation.anim.defaultExitTransition
import com.practicum.list.core.navigation.anim.defaultPopEnterTransition
import com.practicum.list.core.navigation.anim.defaultPopExitTransition
import com.practicum.list.core.theme.R.string
import com.practicum.list.feature.main.presentation.MainEffect
import com.practicum.list.feature.main.presentation.MainIntent
import com.practicum.list.feature.main.presentation.MainViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.mainScreenNavigation(navController: NavController) {
    composable<MainScreenRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition
    ) {
        MainScreenRouteContent(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenRouteContent(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is MainEffect.NavigateToList ->
                    navController.navigate(ListScreenRoute(listId = effect.id))

                is MainEffect.ShowDeleteConfirmation -> Unit

                is MainEffect.ShowRenameDialog -> Unit

                is MainEffect.ShowCategoryPicker -> {
                    scope.launch {
                        sheetState.show()
                    }
                }

                is MainEffect.HideCategoryPicker -> {
                    scope.launch {
                        sheetState.hide()
                    }
                }

                is MainEffect.ShowError ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                title = stringResource(string.title_my_lists),
                onProfileClick = {
                    viewModel.dispatch(MainIntent.ProfileClicked)
                },
            )
        },
    ) { paddingValues ->
        MainScreen(
            state = state,
            onIntent = viewModel::dispatch,
            modifier = Modifier.padding(paddingValues),
            sheetState = sheetState
        )
    }
}
