package com.andreick.dependencyinjection.common.dependencyinjection.app

import android.app.Application
import com.andreick.dependencyinjection.networking.StackoverflowApi
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun application(): Application

    fun stackoverflowApi(): StackoverflowApi
}