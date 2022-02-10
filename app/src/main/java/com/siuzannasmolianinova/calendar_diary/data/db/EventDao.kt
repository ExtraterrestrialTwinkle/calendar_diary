package com.siuzannasmolianinova.calendar_diary.data.db

import androidx.room.*
import com.siuzannasmolianinova.calendar_diary.data.db.entities.Event
import com.siuzannasmolianinova.calendar_diary.data.db.entities.EventContract

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveOneDay(list: List<Event>)

    @Transaction
    fun saveOneDayEmpty(list: List<Event>, date: String): List<Event> {
        saveOneDay(list)
        return openDay(date)
    }

    @Query("SELECT * FROM ${EventContract.TABLE_NAME} WHERE ${EventContract.Columns.DAY} = :date")
    fun openDay(date: String): List<Event>


    @Query("SELECT * FROM ${EventContract.TABLE_NAME} WHERE ${EventContract.Columns.START} = :dateStart")
    fun getEventId(dateStart: Long): Long
}
