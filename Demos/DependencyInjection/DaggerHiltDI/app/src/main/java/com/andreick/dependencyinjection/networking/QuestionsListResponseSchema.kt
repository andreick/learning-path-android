package com.andreick.dependencyinjection.networking

import com.google.gson.annotations.SerializedName
import com.andreick.dependencyinjection.questions.Question

class QuestionsListResponseSchema(@SerializedName("items") val questions: List<Question>)