package com.andreick.coroutinesflow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel: ViewModel() {

    val newsArticles = MutableLiveData<String>()

}