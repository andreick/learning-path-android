package com.andreick.coroutinesflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andreick.coroutinesflow.model.NewsArticleService

class ListViewModel: ViewModel() {

    val newsArticles = NewsArticleService.getNewsArticles().asLiveData()
}