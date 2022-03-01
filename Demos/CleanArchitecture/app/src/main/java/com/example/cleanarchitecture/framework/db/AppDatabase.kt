package com.example.cleanarchitecture.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private const val DATABASE_NAME = "note.db"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: create(context).also { instance = it }

        private fun create(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}