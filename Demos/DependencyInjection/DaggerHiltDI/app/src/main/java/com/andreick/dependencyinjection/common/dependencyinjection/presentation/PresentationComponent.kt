package com.andreick.dependencyinjection.common.dependencyinjection.presentation

import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsActivity
import com.andreick.dependencyinjection.screens.questionslist.QuestionsListFragment
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(fragment: QuestionsListFragment)
    fun inject(activity: QuestionDetailsActivity)
}