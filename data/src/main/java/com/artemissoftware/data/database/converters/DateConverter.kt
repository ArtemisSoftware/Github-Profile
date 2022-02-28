package com.artemissoftware.data.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*

@ProvidedTypeConverter
object DateConverter {
    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}