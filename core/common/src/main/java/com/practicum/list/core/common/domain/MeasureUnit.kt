package com.practicum.list.core.common.domain

import androidx.annotation.StringRes
import com.practicum.list.core.common.R

sealed class MeasureUnit(
    val code: String,
    @StringRes val name: Int
) {
    data object Piece : MeasureUnit("pcs", R.string.unit_piece)
    data object Kilogram : MeasureUnit("kg", R.string.unit_kg)

    data object Package : MeasureUnit("pkg", R.string.unit_pkg)

    data object Milligram : MeasureUnit("mg", R.string.unit_mg)

    data object Liter : MeasureUnit("l", R.string.unit_l)

    companion object {
        val all: List<MeasureUnit> = listOf(Piece, Kilogram, Package, Milligram, Liter)

        fun fromCode(code: String?): MeasureUnit =
            all.firstOrNull { unit -> unit.code == code } ?: Piece
    }
}