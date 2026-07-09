package com.practicum.list.core.data.local.converter

import androidx.room.TypeConverter
import com.practicum.list.core.common.domain.MeasureUnit

class MeasureUnitConverter {
    @TypeConverter
    fun toCode(unit: MeasureUnit): String = unit.code

    @TypeConverter
    fun fromCode(code: String): MeasureUnit = MeasureUnit.fromCode(code)
}
