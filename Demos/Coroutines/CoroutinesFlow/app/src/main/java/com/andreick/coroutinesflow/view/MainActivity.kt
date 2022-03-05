package com.andreick.coroutinesflow.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreick.coroutinesflow.databinding.ActivityMainBinding
import com.andreick.coroutinesflow.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()
    private val newsListAdapter = NewsListAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsListAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.newsArticles.observe(this) { article ->
        }
    }
}
