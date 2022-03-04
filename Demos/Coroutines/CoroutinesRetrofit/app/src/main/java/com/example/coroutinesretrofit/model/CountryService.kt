package com.example.coroutinesretrofit.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryService {

    private const val BASE_URL = "https://raw.githubusercontent.com"

    fun getCountryApi(): CountriesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountriesApi::class.java)
}