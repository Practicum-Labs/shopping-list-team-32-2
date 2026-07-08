package com.practicum.list.feature.list.presentation

import com.practicum.list.core.mvi.MviState

data class ListState(val listId: Long, val listTitle: String = "") : MviState