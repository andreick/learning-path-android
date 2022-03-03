package com.andreick.workingwithapi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreick.workingwithapi.databinding.ItemProductBinding
import com.andreick.workingwithapi.model.Product

class ProductAdapter(
    private val context: Context,
    private val productList: List<Product>
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        with(holder.binding) {
            tvProductName.text = product.prName
            tvProductPrice.text = product.prPrice
            Glide.with(context).load(product.prImage).into(ivProduct)
        }
    }

    override fun getItemCount(): Int = productList.size
}

class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)