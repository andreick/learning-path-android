package com.andreick.dependencyinjection.screens.common

interface ScreenNavigator {

    fun toQuestionDetails(questionId: String)
    fun toViewModel()
    fun navigateBack()
}