package com.siuzannasmolianinova.calendar_diary.presentation.core.utils

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class LocalDateTimeConverter {

    @TypeConverter
    fun localDateTimeToTimestamp(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    @TypeConverter
    fun timestampToLocalDateTime(timeStamp: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())
    }

    fun dayTime(timeStamp: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())

    fun day(dayTime: LocalDateTime) =
        "${dayTime.dayOfMonth} : ${dayTime.monthValue} : ${dayTime.year}"
}
