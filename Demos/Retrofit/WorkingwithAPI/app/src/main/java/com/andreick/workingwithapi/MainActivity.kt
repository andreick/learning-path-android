package com.andreick.workingwithapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreick.workingwithapi.api.MyRetrofit
import com.andreick.workingwithapi.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvProducts = findViewById(R.id.rv_products)
        rvProducts.layoutManager = LinearLayoutManager(this)
        getData()
    }

    private fun getData() {
        val call: Call<List<Product>> = MyRetrofit.instance!!.productApi().getProductApi()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val adapter = response.body()
                    ?.let { ProductAdapter(this@MainActivity, it.toList()) }
                rvProducts.adapter = adapter
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}