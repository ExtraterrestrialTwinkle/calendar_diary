package com.siuzannasmolianinova.calendar_diary.presentation.core.utils

import java.lang.Exception

class TimeConverter {

    fun convertTimeStringToMillis(timeArray: Array<String>, data: String): Pair<Long, Long> {
        lateinit var result: Pair<Long, Long>
        try {
            if (timeArray.contains(data)) {
                val transformed = data.split(" – ")
                val first = transformed[0].trim().split(":")[0].toIntOrNull()
                val second = transformed[1].trim().split(":")[0].toIntOrNull()
                if (first != null && second != null) {
                    result =
                        Pair(first.times(3_600_000).toLong(), second.times(3_600_000).toLong())
                } else {
                    throw IllegalArgumentException("Неожиданный формат данных")
                }
            } else {
                throw IllegalArgumentException("Неожиданный формат данных")
            }
        } catch (t: Throwable) {
            throw Exception("Невозможно обработать данные")
        }
        return result
    }

    fun convertMillisToTimeString(data: Pair<Long, Long>): String {
        val first = data.first.div(3_600_000).toString()
        val second = data.second.div(3_600_000).toString()
        val firstString = helper(first)
        val secondString = helper(second)
        return StringBuilder().append(firstString).append(" – ").append(secondString).toString()
    }

    private fun helper(data: String): String {
        return if (data.length == 1) {
            StringBuilder().append("0").append(data).append(":00").toString()
        } else {
            StringBuilder().append(data).append(":00").toString()
        }
    }
}
