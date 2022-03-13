package com.example.cleanarchitecture.framework.di

import com.example.core.repository.NoteRepository
import com.example.core.usecase.*
import dagger.Module
import dagger.Provides

@Module
object UseCasesModule {
    @Provides
    fun provideAddNote(repository: NoteRepository) = AddNote(repository)

    @Provides
    fun provideGetAllNotes(repository: NoteRepository) = GetAllNotes(repository)

    @Provides
    fun provideGetNote(repository: NoteRepository) = GetNote(repository)

    @Provides
    fun provideRemoveNote(repository: NoteRepository) = RemoveNote(repository)

    @Provides
    fun provideGetWordCount() = GetWordCount()
}