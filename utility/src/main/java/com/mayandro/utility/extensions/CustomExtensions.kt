package com.mayandro.utility.extensions

import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

fun Long.getRoughNumber(): String {
    if (this <= 999) {
        return this.toString()
    }
    val units = arrayOf("", "K", "M", "B", "P")
    val digitGroups = (log10(this.toDouble()) / log10(1000.0)).toInt()
    return DecimalFormat("#,##0.#").format(this / 1000.0.pow(digitGroups.toDouble()))
        .toString() + "" + units[digitGroups]
}