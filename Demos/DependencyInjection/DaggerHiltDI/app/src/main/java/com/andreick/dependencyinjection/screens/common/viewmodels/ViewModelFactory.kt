package com.andreick.dependencyinjection.screens.common.viewmodels

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.andreick.dependencyinjection.questions.FetchQuestionDetailsUseCase
import com.andreick.dependencyinjection.questions.FetchQuestionsUseCase
import com.andreick.dependencyinjection.screens.viewmodel.MyOtherViewModel
import com.andreick.dependencyinjection.screens.viewmodel.MyViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val fetchQuestionDetailsUseCaseProvider: Provider<FetchQuestionDetailsUseCase>,
    private val fetchQuestionsUseCaseProvider: Provider<FetchQuestionsUseCase>,
    savedStateRegistryOwner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        return when (modelClass) {
            MyViewModel::class.java -> MyViewModel(
                fetchQuestionsUseCaseProvider.get(),
                fetchQuestionDetailsUseCaseProvider.get(),
                handle
            ) as T
            MyOtherViewModel::class.java -> MyOtherViewModel(
                fetchQuestionsUseCaseProvider.get()
            ) as T
            else -> throw RuntimeException("Unsupported ViewModel type: $modelClass")
        }
    }
}