package com.andreick.emojidictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TextDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_detail)
    }

    override fun onStart() {
        super.onStart()

        val textTextView = findViewById<TextView>(R.id.textTextView)
        textTextView.text = intent.extras?.getString("emoji")
    }
}