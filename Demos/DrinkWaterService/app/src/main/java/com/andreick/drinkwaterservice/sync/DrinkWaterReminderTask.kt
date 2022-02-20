package com.andreick.drinkwaterservice.sync

import android.content.Context
import com.andreick.drinkwaterservice.utils.PreferencesUtils

object DrinkWaterReminderTask {
    const val ACTION_INCREMENT_WATER_COUNT = "action-increment-water-count"

    fun executeTask(context: Context, action: String?) {
        when (action) {
            ACTION_INCREMENT_WATER_COUNT -> PreferencesUtils.incrementWaterCount(context)
        }
    }
}