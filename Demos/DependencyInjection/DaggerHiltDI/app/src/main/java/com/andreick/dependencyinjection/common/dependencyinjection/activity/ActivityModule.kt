package com.andreick.dependencyinjection.common.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun activity() = activity

    @Provides
    fun layoutInflater(activity: AppCompatActivity) = activity.layoutInflater

    @Provides
    fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager
}