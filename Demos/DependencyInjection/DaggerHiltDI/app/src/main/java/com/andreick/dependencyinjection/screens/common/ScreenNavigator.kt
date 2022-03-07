package com.andreick.dependencyinjection.screens.common

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsActivity

class ScreenNavigator (private val activity: AppCompatActivity) {

    fun toQuestionDetails(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }

    fun navigateBack() {
        activity.onBackPressed()
    }
}