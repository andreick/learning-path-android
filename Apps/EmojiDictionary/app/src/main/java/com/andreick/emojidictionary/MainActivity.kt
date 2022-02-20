package com.andreick.emojidictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: GridLayoutManager
    lateinit var adapter: TextAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager

        adapter = TextAdapter(arrayListOf("&#129300;", "&#128525;", "&#128526;"))
        recyclerView.adapter = adapter
    }
}