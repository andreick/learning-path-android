package com.andreick.dependencyinjection.screens.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreick.dependencyinjection.screens.viewmodel.MyOtherViewModel
import com.andreick.dependencyinjection.screens.viewmodel.MyViewModel
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val myViewModelProvider: Provider<MyViewModel>,
    private val myOtherViewModelProvider: Provider<MyOtherViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when(modelClass) {
            MyViewModel::class.java -> myViewModelProvider.get() as T
            MyOtherViewModel::class.java -> myOtherViewModelProvider.get() as T
            else -> throw RuntimeException("Unsupported ViewModel type: $modelClass")
        }
}