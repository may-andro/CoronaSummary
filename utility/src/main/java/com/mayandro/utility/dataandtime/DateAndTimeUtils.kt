package com.mayandro.utility.dataandtime

import com.mayandro.utility.SERVER_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

fun String.getTimeLong(): Long? {
    val dateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.ENGLISH)
    return try {
        val date: Date = dateFormat.parse(this) ?: throw Exception()
        date.time
    } catch (e: Exception) {
        null
    }
}

fun String.getDate(pattern: String = "yyyyMMdd"): String? {
    val dateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.ENGLISH)
    val targetFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    return try {
        val date = dateFormat.parse(this)
        date?.let {
            targetFormat.format(date)
        } ?: kotlin.run {
            null
        }
    } catch (e: Exception) {
        null
    }
}

fun Long.getTimeDifference(): Long {
    val currentDate = Calendar.getInstance().time
    val givenDate = Date(this)

    val secondsInMilli: Long = 1000
    val minutesInMilli: Long = secondsInMilli * 60

    val different: Long = currentDate.time - givenDate.time
    return different / minutesInMilli
}
