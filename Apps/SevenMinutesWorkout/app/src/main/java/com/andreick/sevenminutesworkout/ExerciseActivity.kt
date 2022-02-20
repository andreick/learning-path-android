package com.andreick.sevenminutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.andreick.sevenminutesworkout.databinding.ActivityExerciseBinding
import com.andreick.sevenminutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

const val REST_COUNTDOWN = 1
const val EXERCISE_COUNTDOWN = 1

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding
    private lateinit var exerciseAdapter: ExerciseStatusAdapter
    private lateinit var tts: TextToSpeech
    private lateinit var countDownTimer: CountDownTimer
    private var player: MediaPlayer? = null
    private var timeOffset = 0

    private var exerciseList = ArrayList<Exercise>()
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbExercise.setNavigationOnClickListener { onBackPressed() }

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()
        setupExerciseStatusRecyclerView()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Log.e("TTS", "The language specified is not supported!")
            } else {
                startRestCountDownTimer()
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    override fun onBackPressed() {
        countDownTimer.cancel()
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        val customDialog = Dialog(this)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCancelable(false)
        dialogBinding.btnConfirm.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            countDownTimer.start()
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView() {
        exerciseAdapter = ExerciseStatusAdapter(exerciseList)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun startRestCountDownTimer() {
        val upcomingExercise = exerciseList[currentExercisePosition + 1].name
        val speechText = "Get ready for $upcomingExercise"
        speakOut(speechText)
        binding.tvUpcomingExerciseName.text = upcomingExercise
        setCountDownTimer(REST_COUNTDOWN) { showExercise() }
    }

    private fun setCountDownTimer(duration: Int, finishAction: () -> Unit) {
        timeOffset = 0
        binding.pbTimer.max = duration
        countDownTimer = object : CountDownTimer(duration * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.pbTimer.progress = duration - timeOffset++
                binding.tvTimer.text = binding.pbTimer.progress.toString()
            }

            override fun onFinish() {
                binding.pbTimer.progress = duration - timeOffset
                binding.tvTimer.text = binding.pbTimer.progress.toString()
                if (currentExercisePosition < exerciseList.lastIndex) {
                    finishAction()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun showExercise() {
        currentExercisePosition++
        exerciseList[currentExercisePosition].isSelected = true
        exerciseAdapter.notifyItemChanged(currentExercisePosition)

        binding.ivExercise.visibility = View.VISIBLE
        binding.tvUpcomingExerciseLabel.visibility = View.GONE
        binding.tvUpcomingExerciseName.visibility = View.GONE

        ConstraintSet().also {
            it.clone(binding.clParent)
            it.clear(binding.flTimer.id, ConstraintSet.TOP)
            it.connect(
                binding.flTimer.id, ConstraintSet.BOTTOM,
                binding.rvExerciseStatus.id, ConstraintSet.TOP,
                20
            )
            it.applyTo(binding.clParent)
        }

        binding.ivExercise.setImageResource(exerciseList[currentExercisePosition].image)
        binding.tvTimerLabel.text = exerciseList[currentExercisePosition].name

        val soundURI = Uri.parse(
            "android.resource://com.andreick.sevenminutesworkout/" + R.raw.press_start
        )
        player = MediaPlayer.create(applicationContext, soundURI)
        player?.isLooping = false
        player?.start()

        setCountDownTimer(EXERCISE_COUNTDOWN) { showRest() }
    }

    private fun showRest() {
        exerciseList[currentExercisePosition].isSelected = false
        exerciseList[currentExercisePosition].isCompleted = true
        exerciseAdapter.notifyItemChanged(currentExercisePosition)

        binding.ivExercise.visibility = View.GONE
        binding.tvUpcomingExerciseLabel.visibility = View.VISIBLE
        binding.tvUpcomingExerciseName.visibility = View.VISIBLE

        ConstraintSet().also {
            it.clone(binding.clParent)
            it.connect(
                binding.flTimer.id, ConstraintSet.TOP,
                binding.tbExercise.id, ConstraintSet.BOTTOM
            )
            it.connect(
                binding.flTimer.id, ConstraintSet.BOTTOM,
                0, ConstraintSet.BOTTOM
            )
            it.applyTo(binding.clParent)
        }

        binding.tvTimerLabel.text = getString(R.string.rest_message)

        startRestCountDownTimer()
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
        player?.stop()
        player?.release()
        countDownTimer.cancel()
        timeOffset = 0
    }
}