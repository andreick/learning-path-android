package com.andreick.dependencyinjection.common.composition

import android.app.Activity
import com.andreick.dependencyinjection.questions.FetchQuestionDetailsUseCase
import com.andreick.dependencyinjection.questions.FetchQuestionsUseCase
import com.andreick.dependencyinjection.screens.common.ScreenNavigator

class ActivityCompositionRoot(
    private val activity: Activity,
    private val appCompositionRoot: AppCompositionRoot
) {
    val screenNavigator by lazy { ScreenNavigator(activity) }

    private val stackoverflowApi get() = appCompositionRoot.stackoverflowApi

    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackoverflowApi)
    val fetchQuestionDetailsUseCase get() = FetchQuestionDetailsUseCase(stackoverflowApi)
}