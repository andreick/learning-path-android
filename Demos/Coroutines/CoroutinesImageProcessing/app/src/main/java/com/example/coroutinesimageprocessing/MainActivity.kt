package com.example.coroutinesimageprocessing

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesimageprocessing.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

private const val IMAGE_URL = "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"

class MainActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coroutineScope.launch {
            val originalDeferred = coroutineScope.async(Dispatchers.IO) { getOriginalBitmap() }
            val originalBitmap = originalDeferred.await()
            val filteredDeferred = coroutineScope.async(Dispatchers.Default) { Filter.apply(originalBitmap) }
            val filteredBitmap = filteredDeferred.await()
            loadImage(filteredBitmap)
        }
    }

    private fun getOriginalBitmap() =
        URL(IMAGE_URL).openStream().use { BitmapFactory.decodeStream(it) }

    private fun loadImage(bitmap: Bitmap) {
        with(binding) {
            progressBar.visibility = View.GONE
            imageView.setImageBitmap(bitmap)
            imageView.visibility = View.VISIBLE
        }
    }
}
