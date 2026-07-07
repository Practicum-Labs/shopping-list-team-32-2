package com.practicum.list.presentation

import com.practicum.list.core.mvi.MviIntent

sealed class RootIntent : MviIntent {
    data object Renew : RootIntent()
    data object AnimationFinished : RootIntent()
}