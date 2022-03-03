package com.andreick.contentproviderclient

import android.database.Cursor
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreick.contentproviderclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvClientList.layoutManager = LinearLayoutManager(this)
        getContentProvider()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.fabClientRefresh.setOnClickListener { getContentProvider() }
    }

    private fun getContentProvider() {
        try {
            val uriString = "content://com.andreick.contentprovider.provider/notes"
            val data = Uri.parse(uriString)
            val cursor = contentResolver.query(
                data, null, null, null, "title"
            )
            if (cursor != null) initRecyclerView(cursor)
        } catch (ex: UnsupportedSchemeException) {
            ex.printStackTrace()
        }
    }

    private fun initRecyclerView(cursor: Cursor) {
        binding.rvClientList.adapter = ClientAdapter(cursor)
    }
}