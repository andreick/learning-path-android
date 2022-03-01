package com.example.cleanarchitecture.framework.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cleanarchitecture.framework.UseCases
import com.example.cleanarchitecture.framework.di.ApplicationModule
import com.example.cleanarchitecture.framework.di.DaggerViewModelComponent
import com.example.core.data.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }

    val noteList = MutableLiveData<List<Note>>()

    fun getAllNotes() {
        coroutineScope.launch {
            val notes = useCases.getAllNotes()
            notes.forEach { it.wordCount = useCases.getWordCount(it) }
            noteList.postValue(notes)
        }
    }
}