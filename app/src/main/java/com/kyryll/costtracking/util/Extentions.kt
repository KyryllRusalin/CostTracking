package com.kyryll.costtracking.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.extractTime(): String {
    val localDateTime = LocalDateTime.parse(this)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return localDateTime.format(timeFormatter)
}

fun String.substringBeforeDot(): String {
    return this.substringBefore(".")
}