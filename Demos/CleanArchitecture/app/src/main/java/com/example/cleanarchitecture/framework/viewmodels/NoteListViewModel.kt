package com.example.cleanarchitecture.framework.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.data.Note
import com.example.core.usecase.GetAllNotes
import com.example.core.usecase.GetWordCount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotes,
    private val getWordCountUseCase: GetWordCount
) : ViewModel() {

    val noteList = MutableLiveData<List<Note>>()

    private val coroutineScope get() = CoroutineScope(Dispatchers.IO)

    fun getAllNotes() {
        coroutineScope.launch {
            val notes = getAllNotesUseCase()
            notes.forEach { it.wordCount = getWordCountUseCase(it) }
            noteList.postValue(notes)
        }
    }

    class Factory @Inject constructor(
        private val noteListViewModelProvider: Provider<NoteListViewModel>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            noteListViewModelProvider.get() as T
    }
}