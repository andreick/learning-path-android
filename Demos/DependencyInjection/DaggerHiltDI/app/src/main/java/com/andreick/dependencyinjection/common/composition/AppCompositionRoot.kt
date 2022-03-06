package com.andreick.dependencyinjection.common.composition

import com.andreick.dependencyinjection.Constants
import com.andreick.dependencyinjection.networking.StackoverflowApi
import com.andreick.dependencyinjection.questions.FetchQuestionDetailsUseCase
import com.andreick.dependencyinjection.questions.FetchQuestionsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppCompositionRoot {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackoverflowApi)
    val fetchQuestionDetailsUseCase get() = FetchQuestionDetailsUseCase(stackoverflowApi)
}