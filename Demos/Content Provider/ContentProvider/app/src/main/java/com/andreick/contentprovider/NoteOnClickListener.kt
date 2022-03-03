package com.andreick.contentprovider

import android.database.Cursor

interface NoteOnClickListener {
    fun onNoteClickItem(cursor: Cursor)
    fun onNoteRemoveItem(cursor: Cursor)
}