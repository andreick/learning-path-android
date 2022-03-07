package com.andreick.dependencyinjection.common.dependencyinjection.activity

import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.andreick.dependencyinjection.networking.StackoverflowApi
import com.andreick.dependencyinjection.screens.common.ScreenNavigator
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun screenNavigator(): ScreenNavigator

    fun layoutInflater(): LayoutInflater

    fun fragmentManager(): FragmentManager

    fun stackoverflowApi(): StackoverflowApi
}