package com.andreick.dependencyinjection.common.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.screens.common.ScreenNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    fun screenNavigator() = ScreenNavigator(activity)

    @Provides
    fun layoutInflater() = activity.layoutInflater

    @Provides
    fun fragmentManager() = activity.supportFragmentManager
}