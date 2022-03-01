package com.example.cleanarchitecture.utilities

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateFormat(pattern: String, locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat(pattern, locale)
    return sdf.format(Date(this))
}