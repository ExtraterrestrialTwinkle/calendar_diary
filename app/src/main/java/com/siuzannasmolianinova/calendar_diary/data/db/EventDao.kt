package com.siuzannasmolianinova.calendar_diary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.siuzannasmolianinova.calendar_diary.data.db.entities.EventContract
import com.siuzannasmolianinova.calendar_diary.data.db.entities.EventExtended

@Dao
interface EventDao {

    @Insert
    fun saveEvent(event: EventExtended)

    @Insert
    fun saveOneDay(list: List<EventExtended>)

    @Query("SELECT * FROM ${EventContract.TABLE_NAME} WHERE ${EventContract.Columns.DAY} = :date")
    fun openDay(date: Long): List<EventExtended>

    @Query("DELETE FROM ${EventContract.TABLE_NAME} WHERE ${EventContract.Columns.ID} = :eventId")
    fun deleteEvent(eventId: Long)

}
