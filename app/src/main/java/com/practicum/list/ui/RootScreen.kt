package com.practicum.list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.practicum.list.R
import com.practicum.list.core.components.placeholder.PlaceholderLayout
import com.practicum.list.presentation.RootIntent
import com.practicum.list.presentation.RootState
import com.practicum.list.ui.components.Onboarding
import kotlinx.coroutines.delay

private const val ONBOARDING_VISIBLE_DURATION = 1500L
private const val ONBOARDING_FADE_OUT_DURATION = 300
private const val ONBOARDING_SCALE = 0.95f

@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
    state: RootState,
    onIntent: (RootIntent) -> Unit
) {
    var onboardingVisible by rememberSaveable { mutableStateOf(false) }
    var screenStarted by remember { mutableStateOf(false) }
    val errorTitle = state.error?.title ?: R.string.something_wrong
    val errorMessage = state.error?.message ?: R.string.cannot_find_profile
    val errorButtonText = state.error?.buttonText ?: R.string.check_again

    LaunchedEffect(Unit) {
        onboardingVisible = true
        delay(ONBOARDING_VISIBLE_DURATION)
        onboardingVisible = false
        screenStarted = true
        delay(ONBOARDING_FADE_OUT_DURATION.toLong())
        onIntent(RootIntent.AnimationFinished)
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = onboardingVisible,
            enter = fadeIn(
                animationSpec = tween(ONBOARDING_FADE_OUT_DURATION)
            ) + scaleIn(
                initialScale = ONBOARDING_SCALE,
                animationSpec = tween(ONBOARDING_FADE_OUT_DURATION)
            ),
            exit = fadeOut(
                animationSpec = tween(ONBOARDING_FADE_OUT_DURATION)
            ) + scaleOut(
                targetScale = ONBOARDING_SCALE,
                animationSpec = tween(ONBOARDING_FADE_OUT_DURATION)
            )
        ) {
            Onboarding()
        }

        if (screenStarted && state.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        state.error?.let {
            PlaceholderLayout(
                modifier = Modifier
                    .fillMaxWidth(),
                imageRes = R.drawable.ic_app_error_285,
                title = stringResource(errorTitle),
                message = stringResource(errorMessage),
                buttonText = stringResource(errorButtonText),
                onButtonClick = { onIntent(RootIntent.Renew) }
            )
        }
    }
}