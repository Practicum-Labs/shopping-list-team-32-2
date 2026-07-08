package com.practicum.list.feature.list.presentation

import com.practicum.list.core.mvi.MviIntent

sealed class ListIntent : MviIntent{
    data object BackClicked: ListIntent()
}