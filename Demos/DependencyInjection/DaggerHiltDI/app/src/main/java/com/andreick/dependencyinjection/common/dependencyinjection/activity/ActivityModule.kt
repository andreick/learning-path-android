package com.andreick.dependencyinjection.common.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.screens.common.ScreenNavigator
import dagger.Module
import dagger.Provides

@Module
object ActivityModule {

    @Provides
    @ActivityScope
    fun screenNavigator(activity: AppCompatActivity) = ScreenNavigator(activity)

    @Provides
    fun layoutInflater(activity: AppCompatActivity) = activity.layoutInflater

    @Provides
    fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager
}