package com.andreick.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvSelectedDate: TextView
    private lateinit var tvDateInMinutes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvDateInMinutes = findViewById(R.id.tvDateInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            tvSelectedDate.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val selectedDateInMinutes = sdf.parse(selectedDate)?.time?.div(60000)

            selectedDateInMinutes?.let {
                val currentDateInMinutes = sdf.parse(sdf.format(System.currentTimeMillis()))?.time?.div(60000)

                currentDateInMinutes?.let {
                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                    tvDateInMinutes.text = differenceInMinutes.toString()
                }
            }
        }, year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 84600000
        dpd.show()
    }
}