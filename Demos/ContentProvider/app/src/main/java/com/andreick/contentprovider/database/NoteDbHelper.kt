package com.andreick.contentprovider.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.andreick.contentprovider.database.NoteContract.NoteEntry

class NoteDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        const val DATABASE_NAME = "Note.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${NoteContract.NoteEntry.TABLE_NAME} (" +
                    "$_ID INTEGER PRIMARY KEY," +
                    "${NoteEntry.COLUMN_NAME_TITLE} TEXT NOT NULL," +
                    "${NoteEntry.COLUMN_NAME_DESCRIPTION} TEXT NOT NULL)"
    }
}