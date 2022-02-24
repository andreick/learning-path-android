package com.example.habittrainer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.example.habittrainer.db.Contract.DATABASE_NAME
import com.example.habittrainer.db.Contract.DATABASE_VERSION
import com.example.habittrainer.db.Contract.HabitEntry

class HabitTrainerDb(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val SQL_CREATE_ENTRIES = "CREATE TABLE ${HabitEntry.TABLE_NAME} (" +
                "$_ID INTEGER PRIMARY KEY," +
                "${HabitEntry.COL_TITLE} TEXT," +
                "${HabitEntry.COL_DESCRIPTION}," +
                "${HabitEntry.COL_IMAGE} INTEGER" +
                ")"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${HabitEntry.TABLE_NAME}"
    }
}