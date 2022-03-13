package com.example.cleanarchitecture

import android.app.Application
import com.example.cleanarchitecture.framework.di.ApplicationComponent
import com.example.cleanarchitecture.framework.di.DaggerApplicationComponent

class MyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }
}