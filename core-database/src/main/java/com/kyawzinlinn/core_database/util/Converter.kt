package com.kyawzinlinn.core_database.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.time.format.TextStyle
import java.util.Locale
@RequiresApi(Build.VERSION_CODES.O)
fun convertDate(time: String): String{
    val inputFormatter = ofPattern("yyyy-MM-dd HH:mm")
    val inputDateTime = LocalDateTime.parse(time,inputFormatter)

    return inputDateTime.toLocalDate().toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertDateToDay(date: String): String {
    val formatter = ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(date, formatter)

    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val monthName = date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return "$dayOfWeek, $monthName"
}

fun getHourFromDateTime(dateTime: String?): String {
    return dateTime?.split(" ")?.last() ?: ""
}

fun String?.removeDecimalPlace() : String {
    return this?.toFloat()?.toInt().toString() ?: "0.0"
}