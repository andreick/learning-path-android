package com.andreick.dependencyinjection.common.dependencyinjection.app

import android.app.Application
import com.andreick.dependencyinjection.Constants
import com.andreick.dependencyinjection.networking.StackoverflowApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(val application: Application) {

    @Provides
    @AppScope
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun application() = application

    @Provides
    @AppScope
    fun stackoverflowApi(retrofit: Retrofit): StackoverflowApi =
        retrofit.create(StackoverflowApi::class.java)
}