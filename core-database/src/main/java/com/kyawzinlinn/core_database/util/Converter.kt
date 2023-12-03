package com.kyawzinlinn.core_database.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun convertDate(time: String): String{
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val inputDateTime = LocalDateTime.parse(time,inputFormatter)

    return inputDateTime.toLocalDate().toString()
}