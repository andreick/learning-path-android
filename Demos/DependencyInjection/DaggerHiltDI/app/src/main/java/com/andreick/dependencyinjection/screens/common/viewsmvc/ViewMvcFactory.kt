package com.andreick.dependencyinjection.screens.common.viewsmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.andreick.dependencyinjection.screens.common.imageloader.ImageLoader
import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsViewMvc
import com.andreick.dependencyinjection.screens.questionslist.QuestionsListViewMvc
import javax.inject.Inject
import javax.inject.Provider

class ViewMvcFactory @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val imageLoaderProvider: Provider<ImageLoader>
) {
    fun newQuestionsListViewMvc(parent: ViewGroup?) = QuestionsListViewMvc(layoutInflater, parent)
    fun newQuestionDetailsViewMvc(parent: ViewGroup?): QuestionDetailsViewMvc {
        val imageLoader1 = imageLoaderProvider.get()
        val imageLoader2 = imageLoaderProvider.get()
        val imageLoader3 = imageLoaderProvider.get()
        return QuestionDetailsViewMvc(layoutInflater, parent, imageLoaderProvider.get())
    }
}