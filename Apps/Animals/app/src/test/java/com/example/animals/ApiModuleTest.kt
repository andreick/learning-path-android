package com.example.animals

import com.example.animals.api.AnimalApi
import com.example.animals.di.ApiModule

class ApiModuleTest(val mockAnimalApi: AnimalApi) : ApiModule() {

    override fun provideAnimalApi(): AnimalApi {
        return mockAnimalApi
    }
}