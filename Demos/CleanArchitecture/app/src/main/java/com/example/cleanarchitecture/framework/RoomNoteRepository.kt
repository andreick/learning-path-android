package com.example.cleanarchitecture.framework

import com.example.cleanarchitecture.framework.db.NoteDao
import com.example.cleanarchitecture.framework.db.NoteEntity
import com.example.core.data.Note
import com.example.core.repository.NoteRepository
import javax.inject.Inject

class RoomNoteRepository @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll() = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}