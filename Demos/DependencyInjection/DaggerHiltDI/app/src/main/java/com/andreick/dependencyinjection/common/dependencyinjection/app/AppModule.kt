package com.andreick.dependencyinjection.common.dependencyinjection.app

import android.app.Application
import com.andreick.dependencyinjection.common.dependencyinjection.MainRetrofit
import com.andreick.dependencyinjection.networking.StackoverflowApi
import com.andreick.dependencyinjection.networking.UrlProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(val application: Application) {

    @Provides
    @AppScope
    @MainRetrofit
    fun retrofit(urlProvider: UrlProvider): Retrofit = Retrofit.Builder()
        .baseUrl(urlProvider.getMainBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @AppScope
    fun otherRetrofit(urlProvider: UrlProvider): Retrofit = Retrofit.Builder()
        .baseUrl(urlProvider.getOtherBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @AppScope
    fun urlProvider() = UrlProvider()

    @Provides
    fun application() = application

    @Provides
    @AppScope
    fun stackoverflowApi(@MainRetrofit retrofit: Retrofit): StackoverflowApi =
        retrofit.create(StackoverflowApi::class.java)
}