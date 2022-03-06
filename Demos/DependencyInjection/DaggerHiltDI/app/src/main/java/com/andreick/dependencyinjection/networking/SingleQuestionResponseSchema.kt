package com.andreick.dependencyinjection.networking

import com.google.gson.annotations.SerializedName
import com.andreick.dependencyinjection.questions.QuestionWithBody

data class SingleQuestionResponseSchema(@SerializedName("items") val questions: List<QuestionWithBody>) {
    val question: QuestionWithBody get() = questions[0]
}