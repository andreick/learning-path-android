package com.example.cleanarchitecture.framework.di

import com.example.cleanarchitecture.framework.RoomNoteRepository
import com.example.core.repository.NoteRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindNoteRepository(roomNoteRepository: RoomNoteRepository): NoteRepository
}