package com.example.animals.api

import com.example.animals.model.Animal
import com.example.animals.model.ApiKey
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    @GET("getKey")
    fun getApiKey(): Single<ApiKey>

    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimals(@Field("key") key: String): Single<List<Animal>>
}