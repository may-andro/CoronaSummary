package com.mayandro.utility.dataandtime

import com.mayandro.utility.SERVER_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

fun String.getTimeLong(locale: Locale): Long? {
    val dateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.ENGLISH)
    return try {
        val date: Date = dateFormat.parse(this) ?: throw Exception()
        date.time
    } catch (e: Exception) {
        null
    }
}

fun String.getDate(): String? {
    val dateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.ENGLISH)
    val targetFormat = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
    val date: Date? = dateFormat.parse(this)
    return try {
        targetFormat.format(date)
    } catch (e: Exception) {
        null
    }
}

fun Long.getTimeDifference(): Long {
    val currentDate = Calendar.getInstance().time
    val givenDate = Date(this)

    val secondsInMilli: Long = 1000
    val minutesInMilli: Long = secondsInMilli * 60

    var different: Long = currentDate.time - givenDate.time
    return different / minutesInMilli
}
