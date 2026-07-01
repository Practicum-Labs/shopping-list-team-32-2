package com.practicum.list.feature.auth.ui.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.practicum.list.core.navigation.LoginRoute
import com.practicum.list.core.navigation.RegisterRoute
import com.practicum.list.core.navigation.ResetPasswordRoute
import com.practicum.list.core.navigation.anim.defaultEnterTransition
import com.practicum.list.core.navigation.anim.defaultExitTransition
import com.practicum.list.core.navigation.anim.defaultPopEnterTransition
import com.practicum.list.core.navigation.anim.defaultPopExitTransition

fun NavGraphBuilder.authScreenNavigation(navController: NavController) {
    composable<LoginRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition
    ) {
        // экран входа
    }

    composable<RegisterRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition
    ) {
        // экран регистрации
    }

    composable<ResetPasswordRoute>(
        enterTransition = defaultEnterTransition,
        exitTransition = defaultExitTransition,
        popEnterTransition = defaultPopEnterTransition,
        popExitTransition = defaultPopExitTransition
    ) {
        // экран сброса пароля
    }
}