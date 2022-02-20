package com.andreick.sevenminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.andreick.sevenminutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

private const val METRIC_UNITS_VIEW = 0
private const val US_UNITS_VIEW = 1

class BmiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding
    private var currentVisibleView = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbBmi)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbBmi.setNavigationOnClickListener { onBackPressed() }

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbMetricUnits.id -> makeVisibleMetricUnitsView()
                binding.rbUsUnits.id -> makeVisibleUsUnitsView()
            }
        }

        binding.btnCalculateUnits.setOnClickListener { calculateUnits() }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilUnitWeight.hint = getString(R.string.weight_kg)
        binding.groupMetricUnits.visibility = View.VISIBLE
        binding.groupUsUnits.visibility = View.INVISIBLE
        binding.groupBmi.visibility = View.INVISIBLE

        binding.etUnitWeight.text?.clear()
        binding.etMetricUnitHeight.text?.clear()
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding.tilUnitWeight.hint = getString(R.string.weight_pounds)
        binding.groupMetricUnits.visibility = View.INVISIBLE
        binding.groupUsUnits.visibility = View.VISIBLE
        binding.groupBmi.visibility = View.INVISIBLE

        binding.etUnitWeight.text?.clear()
        binding.etUsUnitHeightFeet.text?.clear()
        binding.etUsUnitHeightInch.text?.clear()
    }

    private fun calculateUnits() {
        when (currentVisibleView) {
            METRIC_UNITS_VIEW -> {
                if (validateMetricUnits()) {
                    val weightValue = binding.etUnitWeight.text.toString().toFloat()
                    val heightValue = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                    val bmi = weightValue / (heightValue * heightValue)
                    displayBmiResult(bmi)
                } else {
                    Toast.makeText(
                        this, "Please enter both values", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            US_UNITS_VIEW -> {
                if (validateUsUnits()) {
                    val weightValue = binding.etUnitWeight.text.toString().toFloat()
                    val heightValueFeet = binding.etUsUnitHeightFeet.text.toString().toFloat()
                    val heightValueInch = binding.etUsUnitHeightInch.text.toString().toFloat()
                    val heightValue = (heightValueFeet * 12) + heightValueInch
                    val bmi = 703 * (weightValue / (heightValue * heightValue))
                    displayBmiResult(bmi)
                } else {
                    Toast.makeText(
                        this, "Please enter all values", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun displayBmiResult(bmi: Float) {
        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()

        val bmiType: String
        val bmiDescription: String

        when {
            bmi > 35 -> {
                bmiType = "Obese Class || (Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
            bmi > 30 -> {
                bmiType = "Obese Class | (Moderately obese)"
                bmiDescription =
                    "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            bmi > 25 -> {
                bmiType = "Overweight"
                bmiDescription =
                    "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            bmi > 18.5 -> {
                bmiType = "Normal"
                bmiDescription = "Congratulations! You are in a good shape!"
            }
            bmi > 16 -> {
                bmiType = "Underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi > 15 -> {
                bmiType = "Severely underweight"
                bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
            }
            else -> {
                bmiType = "Very severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
        }

        binding.groupBmi.visibility = View.VISIBLE
        binding.tvBmiValue.text = bmiValue
        binding.tvBmiType.text = bmiType
        binding.tvBmiDescription.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding.etUnitWeight.text.toString().isEmpty() ||
            binding.etMetricUnitHeight.text.toString().isEmpty()
        ) {
            isValid = false
        }
        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true
        if (binding.etUnitWeight.text.toString().isEmpty() ||
            binding.etUsUnitHeightFeet.text.toString().isEmpty() ||
            binding.etUsUnitHeightInch.text.toString().isEmpty()
        ) {
            isValid = false
        }
        return isValid
    }
}