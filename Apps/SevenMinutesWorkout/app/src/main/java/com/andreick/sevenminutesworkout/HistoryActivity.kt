package com.andreick.sevenminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreick.sevenminutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    companion object { private const val TAG = "HistoryActivity" }

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbHistory.setNavigationOnClickListener { onBackPressed() }

        val dao = (application as WorkoutApp).database.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(dao: HistoryDao) {
        lifecycleScope.launch {
            dao.fetchAllDates().collect { completedExercises ->
                if (completedExercises.isNotEmpty()) {
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvHistoryNoData.visibility = View.GONE

                    binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList(completedExercises.map { it.date })
                    val historyAdapter = HistoryAdapter(dates)
                    binding.rvHistory.adapter = historyAdapter
                } else {
                    binding.rvHistory.visibility = View.GONE
                    binding.tvHistoryNoData.visibility = View.VISIBLE
                }
            }
        }
    }
}