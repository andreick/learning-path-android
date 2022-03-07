package com.andreick.dependencyinjection.common.dependencyinjection.presentation

import com.andreick.dependencyinjection.networking.StackoverflowApi
import com.andreick.dependencyinjection.questions.FetchQuestionDetailsUseCase
import com.andreick.dependencyinjection.questions.FetchQuestionsUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun fetchQuestionsUseCase(stackoverflowApi: StackoverflowApi) =
        FetchQuestionsUseCase(stackoverflowApi)

    @Provides
    fun fetchQuestionDetailsUseCase(stackoverflowApi: StackoverflowApi) =
        FetchQuestionDetailsUseCase(stackoverflowApi)
}