package com.practicum.list.presentation

import com.practicum.list.core.mvi.MviState

data class RootState(
    val isLoading: Boolean = true,
    val error: RootError? = null
) : MviState
