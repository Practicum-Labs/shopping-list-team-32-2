package com.practicum.list.presentation

import com.practicum.list.core.mvi.MviEffect

sealed class RootEffect : MviEffect {
    data object NavigateToMain : RootEffect()
    data object NavigateToLogin : RootEffect()
}