package com.example.cleanarchitecture.framework.di

import android.app.Application
import com.example.cleanarchitecture.framework.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = AppDatabase.getInstance(application)

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase) = appDatabase.noteDao()
}