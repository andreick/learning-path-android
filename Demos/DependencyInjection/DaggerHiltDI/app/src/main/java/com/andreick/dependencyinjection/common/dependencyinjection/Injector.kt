package com.andreick.dependencyinjection.common.dependencyinjection

import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsActivity
import com.andreick.dependencyinjection.screens.questionslist.QuestionsListFragment

class Injector(private val compositionRoot: PresentationCompositionRoot) {

    fun inject(fragment: QuestionsListFragment) {
        fragment.fetchQuestionsUseCase = compositionRoot.fetchQuestionsUseCase
        fragment.dialogsNavigator = compositionRoot.dialogsNavigator
        fragment.screenNavigator = compositionRoot.screenNavigator
        fragment.viewMvcFactory = compositionRoot.viewMvcFactory
    }

    fun inject(activity: QuestionDetailsActivity) {
        activity.fetchQuestionDetailsUseCase = compositionRoot.fetchQuestionDetailsUseCase
        activity.dialogsNavigator = compositionRoot.dialogsNavigator
        activity.screenNavigator = compositionRoot.screenNavigator
        activity.viewMvcFactory = compositionRoot.viewMvcFactory
    }
}