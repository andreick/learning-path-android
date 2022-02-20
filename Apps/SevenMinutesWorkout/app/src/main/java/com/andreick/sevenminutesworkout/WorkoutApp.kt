package com.andreick.sevenminutesworkout

import android.app.Application

class WorkoutApp : Application() {

    val database by lazy {
        HistoryDatabase.getInstance(this)
    }
}