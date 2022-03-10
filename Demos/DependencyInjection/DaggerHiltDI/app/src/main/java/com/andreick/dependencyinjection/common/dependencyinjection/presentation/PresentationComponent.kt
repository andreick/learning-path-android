package com.andreick.dependencyinjection.common.dependencyinjection.presentation

import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsActivity
import com.andreick.dependencyinjection.screens.questionslist.QuestionsListActivity
import com.andreick.dependencyinjection.screens.questionslist.QuestionsListFragment
import com.andreick.dependencyinjection.screens.viewmodel.ViewModelActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [ViewModelsModule::class])
interface PresentationComponent {

    fun inject(fragment: QuestionsListFragment)
    fun inject(activity: QuestionDetailsActivity)
    fun inject(activity: QuestionsListActivity)
    fun inject(viewModelActivity: ViewModelActivity)
}