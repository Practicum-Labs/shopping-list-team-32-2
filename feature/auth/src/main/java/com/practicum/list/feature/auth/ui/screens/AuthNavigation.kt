package com.practicum.list.feature.auth.ui.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.practicum.list.feature.auth.ui.screens.login.loginScreenNavigation
import com.practicum.list.feature.auth.ui.screens.recovery.resetPasswordScreenNavigation
import com.practicum.list.feature.auth.ui.screens.register.registerScreenNavigation

fun NavGraphBuilder.authScreenNavigation(navController: NavController) {
    loginScreenNavigation(navController)
    registerScreenNavigation(navController)
    resetPasswordScreenNavigation(navController)
}
