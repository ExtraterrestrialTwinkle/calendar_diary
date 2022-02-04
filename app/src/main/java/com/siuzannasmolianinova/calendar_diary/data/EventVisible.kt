package com.siuzannasmolianinova.calendar_diary.data

import org.threeten.bp.Instant

data class EventVisible(
    val id: Long,
    val title: String,
    val dateStart: Instant,
    val dateFinish: Instant
)
