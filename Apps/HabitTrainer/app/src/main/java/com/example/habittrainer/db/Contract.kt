package com.example.habittrainer.db

import android.provider.BaseColumns

object Contract {
    const val DATABASE_NAME = "habittrainer.db"
    const val DATABASE_VERSION = 10

    object HabitEntry : BaseColumns {
        const val TABLE_NAME = "habits"
        const val COL_TITLE = "title"
        const val COL_DESCRIPTION = "description"
        const val COL_IMAGE = "image"
    }
}