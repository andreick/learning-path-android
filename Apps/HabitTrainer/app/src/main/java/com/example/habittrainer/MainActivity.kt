package com.example.habittrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittrainer.databinding.ActivityMainBinding
import com.example.habittrainer.db.HabitDbTable

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.rv) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = HabitsAdapter(HabitDbTable(this@MainActivity).readAllHabits())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_habit -> {
                val intent = Intent(this, CreateHabitActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}