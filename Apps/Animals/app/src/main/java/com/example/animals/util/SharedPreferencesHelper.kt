package com.example.animals.util

import android.content.Context

class SharedPreferencesHelper(context: Context) {

    private val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    fun saveApiKey(key: String?) {
        prefs.edit().putString(PREF_API_KEY, key).apply()
    }

    fun getApiKey() = prefs.getString(PREF_API_KEY, null)

    companion object {
        private const val PREF_FILE_NAME = "com.example.animals.api_key"
        private const val PREF_API_KEY = "api key"
    }
}