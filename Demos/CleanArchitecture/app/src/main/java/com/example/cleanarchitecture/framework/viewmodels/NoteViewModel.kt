package com.example.cleanarchitecture.framework.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchitecture.framework.UseCases
import com.example.core.data.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class NoteViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()

    private val coroutineScope get() = CoroutineScope(Dispatchers.IO)

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }

    fun getNote(id: Long) {
        coroutineScope.launch {
            val note = useCases.getNote(id)
            currentNote.postValue(note)
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            useCases.removeNote(note)
            saved.postValue(true)
        }
    }

    class Factory @Inject constructor(
        private val noteViewModelProvider: Provider<NoteViewModel>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            noteViewModelProvider.get() as T
    }
}