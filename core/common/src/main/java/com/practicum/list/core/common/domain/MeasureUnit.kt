package com.practicum.list.core.common.domain

import androidx.annotation.StringRes
import com.practicum.list.core.common.R

sealed class MeasureUnit(
    val code: String,
    @StringRes val name: Int
) {
    data object Piece : MeasureUnit("pcs", R.string.unit_piece)
    data object Kilogram : MeasureUnit("kg", R.string.unit_kg)
    data object Gram : MeasureUnit("g", R.string.unit_g)
    data object Milligram : MeasureUnit("mg", R.string.unit_mg)
    data object Liter : MeasureUnit("l", R.string.unit_l)
    data object Milliliter : MeasureUnit("ml", R.string.unit_ml)
    data object Package : MeasureUnit("pkg", R.string.unit_pkg)

    companion object {
        val all: List<MeasureUnit> = listOf(
            Piece,
            Kilogram,
            Gram,
            Milligram,
            Liter,
            Milliliter,
            Package,
        )

        fun fromCode(code: String?): MeasureUnit =
            all.firstOrNull { unit -> unit.code == code } ?: Piece
    }
}