package com.andreick.vmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var _count = 0
    var count = MutableLiveData<String>().apply { value = "$_count" }
        private set

    fun nextCount() {
        _count++
        if (_count > 5) _count = 0
        count.value = "$_count"
    }
}