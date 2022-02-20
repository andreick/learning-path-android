package com.andreick.vmodel

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.andreick.vmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupOnClick()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.count.observe(this) { count ->
            binding.edCount.setText(count)
        }
    }

    private fun setupOnClick() {
        binding.btnData.setOnClickListener { viewModel.nextCount() }
        binding.btnShow.setOnClickListener {
            displayToast("Count value: ${viewModel.count.value}")
        }
    }

    private fun displayToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}