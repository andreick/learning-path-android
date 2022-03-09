package com.andreick.dependencyinjection.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreick.dependencyinjection.questions.FetchQuestionsUseCase
import com.andreick.dependencyinjection.questions.Question
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase
): ViewModel() {

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
}