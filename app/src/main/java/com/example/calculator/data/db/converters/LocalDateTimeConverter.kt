package com.example.calculator.data.db.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    @TypeConverter
    fun toString(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun toLocalDateTime(str: String?): LocalDateTime? {
        return LocalDateTime.parse(str, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}