package com.andreick.dependencyinjection.screens.common

import android.app.Activity
import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsActivity

class ScreenNavigator(private val activity: Activity) {

    fun toQuestionDetails(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }

    fun navigateBack() {
        activity.onBackPressed()
    }
}