package com.siuzannasmolianinova.calendar_diary.data

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class LocalDateTimeConverter {

    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun stringToLocalDateTime(
        value: String
    ): LocalDateTime {
        return LocalDateTime.parse(value, dateTimeFormatter)
    }

    @TypeConverter
    fun localDateTimeToString(
        localDateTime: LocalDateTime
    ): String {
        return dateTimeFormatter.format(localDateTime)
    }

    fun localDateTimeToDateTime(
        localDateTime: LocalDateTime
    ): List<String> {
        return localDateTimeToString(localDateTime).split('T')
    }

    fun localDateTimeToHoursMinutes(
        localDateTime: LocalDateTime
    ): List<String> {
        return localDateTimeToDateTime(localDateTime)[1].split(':')
    }

}
