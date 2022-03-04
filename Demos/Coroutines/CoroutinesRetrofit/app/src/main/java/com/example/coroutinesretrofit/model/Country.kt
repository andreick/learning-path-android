package com.example.coroutinesretrofit.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val countryName: String?,
    @SerializedName("capital") val capital: String?,
    @SerializedName("flag") val flag: String?
)