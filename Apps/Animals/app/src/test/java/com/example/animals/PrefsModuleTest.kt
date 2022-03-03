package com.example.animals

import android.app.Application
import com.example.animals.di.PrefsModule
import com.example.animals.util.SharedPreferencesHelper

class PrefsModuleTest(val mockPrefs: SharedPreferencesHelper) : PrefsModule() {

    override fun provideAppSharedPreferences(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}