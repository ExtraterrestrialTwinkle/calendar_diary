package com.siuzannasmolianinova.calendar_diary.data.db.entities

import android.provider.CalendarContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EventContract.TABLE_NAME)
data class EventExtended(
    @PrimaryKey
    @ColumnInfo(name = EventContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = EventContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = EventContract.Columns.START)
    val dateStart: org.threeten.bp.LocalDateTime,
    @ColumnInfo(name = EventContract.Columns.FINISH)
    val dateFinish: org.threeten.bp.LocalDateTime,
    @ColumnInfo(name = EventContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = EventContract.Columns.DAY)
    val day: Long
)
