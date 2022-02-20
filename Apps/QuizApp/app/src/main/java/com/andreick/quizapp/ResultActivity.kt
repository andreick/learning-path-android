package com.andreick.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvUsername = findViewById<TextView>(R.id.tvResultUsername)
        val tvScore = findViewById<TextView>(R.id.tvResultScore)
        val btnFinish = findViewById<Button>(R.id.btnResultFinish)

        val username = intent.getStringExtra(Constants.USER_NAME)
        val score = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        tvUsername.text = username
        tvScore.text = "Your score is $score out of $totalQuestions"

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}