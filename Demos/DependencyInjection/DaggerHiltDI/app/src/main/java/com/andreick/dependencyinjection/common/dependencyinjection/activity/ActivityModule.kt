package com.andreick.dependencyinjection.common.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.common.dependencyinjection.app.AppComponent
import com.andreick.dependencyinjection.screens.common.ScreenNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val activity: AppCompatActivity,
    private val appComponent: AppComponent
) {
    private val screenNavigator by lazy { ScreenNavigator(activity) }

    @Provides
    fun screenNavigator() = screenNavigator

    @Provides
    fun application() = appComponent.application()

    @Provides
    fun layoutInflater() = activity.layoutInflater

    @Provides
    fun fragmentManager() = activity.supportFragmentManager

    @Provides
    fun stackoverflowApi() = appComponent.stackoverflowApi()
}