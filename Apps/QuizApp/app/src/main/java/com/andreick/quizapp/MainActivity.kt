package com.andreick.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var edUsername: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnMainStart)
        edUsername = findViewById(R.id.edMainUsername)

        btnStart.setOnClickListener {
            if (edUsername.text.isBlank()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, edUsername.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}