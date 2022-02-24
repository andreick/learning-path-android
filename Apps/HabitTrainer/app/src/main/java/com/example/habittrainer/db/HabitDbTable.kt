package com.example.habittrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.provider.BaseColumns._ID
import com.example.habittrainer.Habit
import com.example.habittrainer.db.Contract.HabitEntry
import com.example.habittrainer.db.Contract.HabitEntry.COL_DESCRIPTION
import com.example.habittrainer.db.Contract.HabitEntry.COL_IMAGE
import com.example.habittrainer.db.Contract.HabitEntry.COL_TITLE
import com.example.habittrainer.extensions.*

class HabitDbTable(context: Context) {

    private val dbHelper = HabitTrainerDb(context)

    fun store(habit: Habit): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE, habit.title)
            put(COL_DESCRIPTION, habit.description)
            put(COL_IMAGE, habit.image.toByteArray())
        }
        return db.doTransaction { insert(HabitEntry.TABLE_NAME, null, values) }
    }

    fun readAllHabits(): List<Habit> {
        val columns = arrayOf(_ID, COL_TITLE, COL_DESCRIPTION, COL_IMAGE)
        val order = "$_ID ASC"
        val db = dbHelper.readableDatabase
        val cursor = db.doQuery(HabitEntry.TABLE_NAME, columns, orderBy = order)
        return parseHabitsFrom(cursor)
    }

    private fun parseHabitsFrom(cursor: Cursor): MutableList<Habit> {
        val habits = mutableListOf<Habit>()
        with(cursor) {
            while (moveToNext()) {
                val title = getStringOrNull(COL_TITLE) ?: ""
                val desc = getStringOrNull(COL_DESCRIPTION) ?: ""
                val image = getBitMapOrNull(COL_IMAGE) ?:
                    Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888)
                habits.add(Habit(title, desc, image))
            }
            close()
        }
        return habits
    }
}