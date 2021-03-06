package com.siuzannasmolianinova.calendar_diary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.siuzannasmolianinova.calendar_diary.presentation.core.utils.LocalDateTimeConverter
import com.siuzannasmolianinova.calendar_diary.data.db.CalendarDatabase.Companion.DB_VERSION
import com.siuzannasmolianinova.calendar_diary.data.db.entities.Event

@TypeConverters(LocalDateTimeConverter::class)
@Database(
    entities = [Event::class],
    version = DB_VERSION
)
abstract class CalendarDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "calendar_database"
    }
}
