package com.mcmouse88.cryptoapp.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTime(time: Long?): String {
    if (time == null) return ""
    val stamp = Timestamp(time * 1000)
    val date = Date(stamp.time)
    val pattern = "HH:mm:ss" // С большой буквы часы то 24-й формат, с маленькой - 12
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(date)
}