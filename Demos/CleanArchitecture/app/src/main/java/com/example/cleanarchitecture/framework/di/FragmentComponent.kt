package com.example.cleanarchitecture.framework.di

import com.example.cleanarchitecture.presentation.NoteFragment
import com.example.cleanarchitecture.presentation.NoteListFragment
import dagger.Subcomponent

@Subcomponent(modules = [RepositoryModule::class, UseCasesModule::class])
interface FragmentComponent {
    fun inject(noteListFragment: NoteListFragment)
    fun inject(noteFragment: NoteFragment)
}