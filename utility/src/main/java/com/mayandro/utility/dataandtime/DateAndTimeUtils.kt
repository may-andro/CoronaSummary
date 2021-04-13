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

fun String.getDate(pattern: String = "yyyyMMdd", inputPattern: String = SERVER_DATE_FORMAT): String? {
    val dateFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
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

fun getServerRequestDate(duration: Int): String {
    val targetFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.ENGLISH)

    val calendar: Calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    calendar.add(Calendar.DAY_OF_YEAR, duration)
    val date = calendar.time
    return targetFormat.format(date)
}
