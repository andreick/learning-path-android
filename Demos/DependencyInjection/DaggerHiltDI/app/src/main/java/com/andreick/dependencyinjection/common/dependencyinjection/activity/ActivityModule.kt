package com.andreick.dependencyinjection.common.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.screens.common.ScreenNavigator
import com.andreick.dependencyinjection.screens.common.ScreenNavigatorImplementation
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule {

    @ActivityScope
    @Binds
    abstract fun screenNavigator(screenNavigator: ScreenNavigatorImplementation): ScreenNavigator

    companion object {
        @Provides
        fun layoutInflater(activity: AppCompatActivity) = activity.layoutInflater

        @Provides
        fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager
    }
}