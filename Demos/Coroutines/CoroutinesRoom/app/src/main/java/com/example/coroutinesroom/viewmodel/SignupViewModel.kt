package com.example.coroutinesroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signUp(username: String, password: String, info: String) {

    }
}