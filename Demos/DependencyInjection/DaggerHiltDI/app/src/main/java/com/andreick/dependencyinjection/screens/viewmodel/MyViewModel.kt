package com.andreick.dependencyinjection.screens.viewmodel

import androidx.lifecycle.*
import com.andreick.dependencyinjection.questions.FetchQuestionDetailsUseCase
import com.andreick.dependencyinjection.questions.FetchQuestionsUseCase
import com.andreick.dependencyinjection.questions.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase,
    private val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase,
    handle: SavedStateHandle
) : ViewModel() {

    private val _questions: MutableLiveData<List<Question>> = handle.getLiveData(QUESTIONS_KEY)
    val question: LiveData<List<Question>> get() = _questions

    init {
        viewModelScope.launch {
            delay(5000)
            when (val result = fetchQuestionsUseCase.fetchLatestQuestions()) {
                is FetchQuestionsUseCase.Result.Success -> _questions.value = result.questions
                FetchQuestionsUseCase.Result.Failure -> throw RuntimeException("fetch failed")
            }
        }
    }

    companion object {
        private const val QUESTIONS_KEY = "questions"
    }
}