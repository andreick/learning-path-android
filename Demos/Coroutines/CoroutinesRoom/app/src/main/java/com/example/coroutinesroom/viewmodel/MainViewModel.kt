package com.example.coroutinesroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val userDeleted = MutableLiveData<Boolean>()
    val signOut = MutableLiveData<Boolean>()

    fun onSignOut() {

    }

    fun onDeleteUser() {

    }
}