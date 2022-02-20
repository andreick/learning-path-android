package com.andreick.coroutines

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var customProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBackgroundExecution: Button = findViewById(R.id.btnBackgroundExecution)
        btnBackgroundExecution.setOnClickListener {
            showProgressDialog()
            lifecycleScope.launch { execute("Task executed successfully") }
        }
    }

    private suspend fun execute(result: String) {
        withContext(Dispatchers.IO) {
            for (i in 1..100_000) { Log.e("delay : ", "$i") }
            runOnUiThread {
                customProgressDialog.dismiss()
                Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showProgressDialog() {
        customProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog.setContentView(R.layout.dialog_custom_progress)
        customProgressDialog.setCancelable(false)

        //Start the dialog and display it on screen.
        customProgressDialog.show()
    }
}