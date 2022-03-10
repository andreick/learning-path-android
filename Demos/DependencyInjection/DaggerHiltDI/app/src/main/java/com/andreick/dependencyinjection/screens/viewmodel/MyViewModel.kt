package com.andreick.dependencyinjection.screens.viewmodel

import androidx.lifecycle.*
import com.andreick.dependencyinjection.questions.FetchQuestionDetailsUseCase
import com.andreick.dependencyinjection.questions.FetchQuestionsUseCase
import com.andreick.dependencyinjection.questions.Question
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class MyViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase,
    private val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
) : ViewModel() {

    private val _questions = MutableLiveData<List<Question>>()
    val question: LiveData<List<Question>> = _questions

    init {
        viewModelScope.launch {
            when (val result = fetchQuestionsUseCase.fetchLatestQuestions()) {
                is FetchQuestionsUseCase.Result.Success -> _questions.value = result.questions
                FetchQuestionsUseCase.Result.Failure -> throw RuntimeException("fetch failed")
            }
        }
    }

    class Factory @Inject constructor(
        private val myViewModelProvider: Provider<MyViewModel>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            myViewModelProvider.get() as T
    }
}