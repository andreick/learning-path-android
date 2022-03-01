package com.example.cleanarchitecture.framework

import android.content.Context
import com.example.cleanarchitecture.framework.db.AppDatabase
import com.example.cleanarchitecture.framework.db.NoteEntity
import com.example.core.data.Note
import com.example.core.repository.NoteDataSource

class RoomNoteDataSource(context: Context) : NoteDataSource {

    private val noteDao = AppDatabase.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll() = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}