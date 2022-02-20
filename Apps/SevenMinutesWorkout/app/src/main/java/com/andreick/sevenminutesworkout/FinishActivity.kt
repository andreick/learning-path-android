package com.andreick.sevenminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.andreick.sevenminutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    companion object { private const val TAG = "FinishActivity" }

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbFinish)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbFinish.setNavigationOnClickListener { onBackPressed() }

        val dao = (application as WorkoutApp).database.historyDao()
        addDateToDatabase(dao)

        binding.btnFinish.setOnClickListener { finish() }
    }

    private fun addDateToDatabase(dao: HistoryDao) {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(Calendar.getInstance().time.also { Log.d(TAG, "Date: $it") })
        Log.d(TAG, "Formatted Date: $date")

        lifecycleScope.launch {
            dao.insert(HistoryEntity(date))
            Log.d(TAG, "Date added")
        }
    }
}