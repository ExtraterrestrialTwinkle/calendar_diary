package com.siuzannasmolianinova.calendar_diary.presentation.core.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeConverterTest {

    private val timeConverter = TimeConverter()

    private val timeArray =
        arrayOf("00:00 – 01:00", "01:00 – 02:00", "02:00 – 03:00", "03:00 – 04:00", "04:00 – 05:00")
    private val emptyArray = emptyArray<String>()
    private val arrayWithAnotherValues = arrayOf("00:00-01.00", "01.00 -02:00")

    private val emptyString = ""
    private val rightString = "01:00 – 02:00"
    private val wrongString = "00:00-01.00"

    @Test
    fun rightTest() {
        val millis = timeConverter.convertTimeStringToMillis(timeArray, rightString)
        val timeString = timeConverter.convertMillisToTimeString(millis)

        assertEquals(rightString, timeString)
    }

    @Test(expected = Exception::class)
    fun emptyArrayTest() {
        val millis = timeConverter.convertTimeStringToMillis(emptyArray, emptyString)
    }

    @Test(expected = Exception::class)
    fun wrongArrayTest() {
        val millis = timeConverter.convertTimeStringToMillis(arrayWithAnotherValues, wrongString)
    }
}
