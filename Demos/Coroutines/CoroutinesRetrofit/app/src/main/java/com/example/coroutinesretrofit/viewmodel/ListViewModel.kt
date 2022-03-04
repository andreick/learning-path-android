package com.example.coroutinesretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesretrofit.model.Country
import com.example.coroutinesretrofit.model.CountryService
import kotlinx.coroutines.*

class ListViewModel: ViewModel() {

    private val countryService = CountryService.getCountryApi()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Error: ${throwable.localizedMessage}")
    }
    private var job: Job? = null

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = countryService.getCountries()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    countries.value = response.body()
                    countryLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        countryLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}