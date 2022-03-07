package com.andreick.dependencyinjection.screens.common

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityScope
import com.andreick.dependencyinjection.screens.questiondetails.QuestionDetailsActivity
import javax.inject.Inject

@ActivityScope
class ScreenNavigator @Inject constructor(private val activity: AppCompatActivity) {

    fun toQuestionDetails(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }

    fun navigateBack() {
        activity.onBackPressed()
    }
}