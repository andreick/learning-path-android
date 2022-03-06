package com.andreick.dependencyinjection.common.composition

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.questions.FetchQuestionDetailsUseCase
import com.andreick.dependencyinjection.questions.FetchQuestionsUseCase
import com.andreick.dependencyinjection.screens.common.ScreenNavigator
import com.andreick.dependencyinjection.screens.common.dialogs.DialogsNavigator

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {
    val screenNavigator by lazy { ScreenNavigator(activity) }

    private val fragmentManager get() = activity.supportFragmentManager
    val dialogsNavigator get() = DialogsNavigator(fragmentManager)

    private val stackoverflowApi get() = appCompositionRoot.stackoverflowApi
    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackoverflowApi)
    val fetchQuestionDetailsUseCase get() = FetchQuestionDetailsUseCase(stackoverflowApi)
}