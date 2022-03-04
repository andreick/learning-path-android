package com.example.coroutinesretrofit.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesretrofit.databinding.ItemCountryBinding
import com.example.coroutinesretrofit.model.Country

class CountryListAdapter(var countries: ArrayList<Country>): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CountryViewHolder(ItemCountryBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class CountryViewHolder(private val binding: ItemCountryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.name.text = country.countryName
            binding.capital.text = country.capital
            binding.imageView.loadImage(country.flag)
        }
    }
}