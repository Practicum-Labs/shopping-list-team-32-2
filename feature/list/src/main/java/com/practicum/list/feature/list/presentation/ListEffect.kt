package com.practicum.list.feature.list.presentation

import com.practicum.list.core.common.domain.MeasureUnit
import com.practicum.list.core.mvi.MviEffect

sealed class ListEffect : MviEffect {
    data object NavigateToMain : ListEffect()
    data class ShowError(val message: String) : ListEffect()

    data class ShoEditBottomSheet(val quantity: Float, val units: MeasureUnit ) : ListEffect()

    data object ShowContextMenu : ListEffect()

    data class ShowConfirmationDialog(val id: Long) : ListEffect()

    data object ShowProductAddBottomSheet: ListEffect()
}