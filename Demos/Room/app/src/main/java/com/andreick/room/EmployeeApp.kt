package com.andreick.room

import android.app.Application

class EmployeeApp : Application() {
    val database by lazy {
        EmployeeDatabase.getInstance(this)
    }
}