package com.siuzannasmolianinova.calendar_diary.data.db.entities

object EventContract {
    const val TABLE_NAME = "event"

    object Columns {
        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val START = "date_start"
        const val FINISH = "date_finish"
        const val DAY = "day"
    }
}
