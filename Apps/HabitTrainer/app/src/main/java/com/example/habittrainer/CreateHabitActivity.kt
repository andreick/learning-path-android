package com.example.habittrainer

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.habittrainer.databinding.ActivityCreateHabitBinding
import com.example.habittrainer.db.HabitDbTable
import com.example.habittrainer.extensions.showToast

class CreateHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateHabitBinding

    private val chooseImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                binding.ivImage.setImageURI(result.data?.data)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnChooseImage.setOnClickListener { chooseImage() }
        binding.btnSave.setOnClickListener { storeHabit() }
    }

    private fun chooseImage() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        val chooser = Intent.createChooser(intent, "Choose image for habit")
        chooseImageLauncher.launch(chooser)
    }

    private fun storeHabit() {
        with(binding) {
            when {
                etTitle.text.isBlank() || etDescription.text.isBlank() ->
                    showToast("Your habit needs an title and description")
                ivImage.drawable == null ->
                    showToast("Enter a motivating picture to your habit")
                else -> {
                    val title = etTitle.text.toString()
                    val description = etDescription.text.toString()
                    val habit = Habit(title, description, ivImage.drawable.toBitmap())

                    val id = HabitDbTable(this@CreateHabitActivity).store(habit)

                    if (id == -1L) showToast("Habit could not be stored...")
                    else {
                        val intent = Intent(this@CreateHabitActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}