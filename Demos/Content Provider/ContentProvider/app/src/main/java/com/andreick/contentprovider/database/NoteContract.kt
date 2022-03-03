package com.andreick.contentprovider.database

import android.provider.BaseColumns

object NoteContract {
    object NoteEntry : BaseColumns {
        const val TABLE_NAME = "Note"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DESCRIPTION = "description"
    }
}