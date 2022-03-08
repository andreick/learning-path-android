package com.andreick.dependencyinjection.screens.common

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsActivity
import javax.inject.Inject

class ScreenNavigatorImplementation @Inject constructor(private val activity: AppCompatActivity): ScreenNavigator {

    override fun toQuestionDetails(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }

    override fun navigateBack() {
        activity.onBackPressed()
    }
}