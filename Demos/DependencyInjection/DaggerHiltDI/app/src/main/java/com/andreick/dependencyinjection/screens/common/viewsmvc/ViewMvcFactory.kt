package com.andreick.dependencyinjection.screens.common.viewsmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsViewMvc
import com.andreick.dependencyinjection.screens.questionslist.QuestionsListViewMvc

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {

    fun newQuestionsListViewMvc(parent: ViewGroup?) = QuestionsListViewMvc(layoutInflater, parent)
    fun newQuestionDetailsViewMvc(parent: ViewGroup?) = QuestionDetailsViewMvc(layoutInflater, parent)
}