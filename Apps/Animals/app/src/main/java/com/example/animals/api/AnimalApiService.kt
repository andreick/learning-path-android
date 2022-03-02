package com.example.animals.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AnimalApiService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    val animalApi: AnimalApi = retrofit.create(AnimalApi::class.java)

    companion object {
        const val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"
    }
}