package com.siuzannasmolianinova.calendar_diary.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import com.siuzannasmolianinova.calendar_diary.data.LocalDateTimeConverter

@TypeConverters(LocalDateTimeConverter::class)
object Database {
    lateinit var instance: CalendarDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            CalendarDatabase::class.java,
            CalendarDatabase.DB_NAME
        )
            .build()
    }
}
