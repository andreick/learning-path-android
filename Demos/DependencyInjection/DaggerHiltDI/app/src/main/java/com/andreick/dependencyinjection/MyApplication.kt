package com.andreick.dependencyinjection

import android.app.Application
import com.andreick.dependencyinjection.common.dependencyinjection.AppCompositionRoot

class MyApplication : Application() {

    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        super.onCreate()
        appCompositionRoot = AppCompositionRoot()
    }
}