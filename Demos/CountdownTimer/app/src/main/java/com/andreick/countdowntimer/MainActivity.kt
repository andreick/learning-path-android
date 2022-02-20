package com.andreick.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.andreick.countdowntimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer
    private var timerDuration: Long = 60_000
    private var pauseOffset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTimer.text = (timerDuration / 1000).toString()

        binding.btnStart.setOnClickListener { startTimer() }
        binding.btnPause.setOnClickListener { countDownTimer.cancel() }
        binding.btnReset.setOnClickListener { resetTimer() }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timerDuration - pauseOffset, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerDuration - millisUntilFinished
                binding.tvTimer.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Timer is finished", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun resetTimer() {
        countDownTimer.cancel()
        binding.tvTimer.text = (timerDuration / 1000).toString()
        pauseOffset = 0
    }
}