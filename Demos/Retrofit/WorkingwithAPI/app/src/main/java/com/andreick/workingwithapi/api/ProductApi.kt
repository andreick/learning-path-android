package com.andreick.workingwithapi.api

import com.andreick.workingwithapi.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET("getdata.php")
    fun getProductApi(): Call<List<Product>>
}