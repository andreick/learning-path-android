package com.andreick.dependencyinjection.common.dependencyinjection.presentation

import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsActivity
import com.andreick.dependencyinjection.screens.questionslist.QuestionsListFragment
import dagger.Component

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(fragment: QuestionsListFragment)
    fun inject(activity: QuestionDetailsActivity)
}