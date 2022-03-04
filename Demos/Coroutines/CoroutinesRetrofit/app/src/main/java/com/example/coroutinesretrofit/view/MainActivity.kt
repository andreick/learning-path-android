package com.example.coroutinesretrofit.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesretrofit.databinding.ActivityMainBinding
import com.example.coroutinesretrofit.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()
    private val countriesAdapter = CountryListAdapter(arrayListOf())
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.refresh()

        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this) { countries ->
            countries?.let {
                binding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        }

        viewModel.countryLoadError.observe(this) { isError ->
            binding.listError.visibility = if (isError.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        viewModel.loading.observe(this) { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.countriesList.visibility = View.GONE
                }
            }
        }
    }
}
