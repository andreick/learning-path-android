package com.example.animals.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.animals.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    fun provideAppSharedPreferences(app: Application) = SharedPreferencesHelper(app)

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPreferences(activity: AppCompatActivity) = SharedPreferencesHelper(activity)

    companion object {
        const val CONTEXT_APP = "Application context"
        const val CONTEXT_ACTIVITY = "Activity context"
    }
}

@Qualifier
annotation class TypeOfContext(val type: String)