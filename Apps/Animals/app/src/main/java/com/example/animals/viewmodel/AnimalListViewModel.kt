package com.example.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animals.api.AnimalApiService
import com.example.animals.model.Animal
import com.example.animals.model.ApiKey
import com.example.animals.util.SharedPreferencesHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class AnimalListViewModel(application: Application) : AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val animalApi = AnimalApiService().animalApi
    private val prefs = SharedPreferencesHelper(getApplication())
    private var invalidApiKey = false

    fun refresh() {
        loading.value = true
        loadError.value = false
        invalidApiKey = false
        val key = prefs.getApiKey()
        if (key == null) getKey()
        else getAnimals(key)
    }

    fun hardRefresh() {
        loading.value = true
        loadError.value = false
        getKey()
    }

    private fun getKey() {
        disposable.add(
            animalApi.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(apiKey: ApiKey) {
                        if (apiKey.key.isNullOrEmpty()) {
                            hasError()
                        } else {
                            getAnimals(apiKey.key)
                            prefs.saveApiKey(apiKey.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        hasError()
                    }
                })
        )
    }

    private fun hasError() {
        animals.value = null
        loading.value = false
        loadError.value = true
    }

    private fun getAnimals(key: String) {
        disposable.add(
            animalApi.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        animals.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            hasError()
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}