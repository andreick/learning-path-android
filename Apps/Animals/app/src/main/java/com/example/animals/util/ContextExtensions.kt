package com.example.animals.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun Context.getProgressDrawable() = CircularProgressDrawable(this).apply {
    strokeWidth = 10f
    centerRadius = 50f
    start()
}