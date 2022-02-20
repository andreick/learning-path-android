package com.andreick.drinkwaterservice

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.andreick.drinkwaterservice.databinding.ActivityMainBinding
import com.andreick.drinkwaterservice.sync.DrinkWaterReminderIntentService
import com.andreick.drinkwaterservice.sync.DrinkWaterReminderTask
import com.andreick.drinkwaterservice.utils.PreferencesUtils

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateWaterCount()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.registerOnSharedPreferenceChangeListener(this)

        binding.ivCupIcon.setOnClickListener { incrementWaterHandle() }
    }

    private fun updateWaterCount() {
        val count = PreferencesUtils.getWaterCount(this)
        binding.tvQuantity.text = "$count"
    }

    private fun incrementWaterHandle() {
        val intent = Intent(this, DrinkWaterReminderIntentService::class.java)
        intent.action = DrinkWaterReminderTask.ACTION_INCREMENT_WATER_COUNT
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            PreferencesUtils.KEY_WATER_COUNT -> updateWaterCount()
        }
    }
}