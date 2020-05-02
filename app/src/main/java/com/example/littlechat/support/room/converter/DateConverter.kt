package com.example.littlechat.support.room.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? = dateLong?.let { Date(it) }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
}
