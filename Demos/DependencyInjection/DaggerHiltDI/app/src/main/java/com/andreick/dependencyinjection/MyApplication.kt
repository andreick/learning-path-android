package com.andreick.dependencyinjection

import android.app.Application
import com.andreick.dependencyinjection.common.dependencyinjection.app.AppComponent
import com.andreick.dependencyinjection.common.dependencyinjection.app.AppModule
import com.andreick.dependencyinjection.common.dependencyinjection.DaggerAppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}