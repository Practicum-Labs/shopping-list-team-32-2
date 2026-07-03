package com.practicum.list.core.navigation.anim

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val ANIMATION_DURATION_MS = 300

val defaultEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(ANIMATION_DURATION_MS)
        )
    }

val defaultExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(ANIMATION_DURATION_MS)
        )
    }

val defaultPopEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(ANIMATION_DURATION_MS)
        )
    }

val defaultPopExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(ANIMATION_DURATION_MS)
        )
    }