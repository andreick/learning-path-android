package com.andreick.dependencyinjection.common.dependencyinjection.app

import com.andreick.dependencyinjection.common.dependencyinjection.MainRetrofit
import com.andreick.dependencyinjection.networking.StackoverflowApi
import com.andreick.dependencyinjection.networking.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

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
    @AppScope
    fun stackoverflowApi(@MainRetrofit retrofit: Retrofit): StackoverflowApi =
        retrofit.create(StackoverflowApi::class.java)
}