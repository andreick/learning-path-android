package com.andreick.dialogs

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageButton = findViewById<ImageButton>(R.id.image_button)
        val btnAlertDialog = findViewById<Button>(R.id.btn_alert_dialog)
        val btnCustomDialog = findViewById<Button>(R.id.btn_custom_dialog)
        val btnCustomProgressDialog = findViewById<Button>(R.id.btn_custom_progress_dialog)

        imageButton.setOnClickListener { view ->
            Snackbar.make(view, "You have clicked image button.", Snackbar.LENGTH_LONG).show()
        }
        btnAlertDialog.setOnClickListener { alertDialogFunction() }
        btnCustomDialog.setOnClickListener { customDialogFunction() }
        btnCustomProgressDialog.setOnClickListener { customProgressDialogFunction() }
    }

    private fun alertDialogFunction() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Alert")
        //set message for alert dialog
        builder.setMessage("This is Alert Dialog. Which is used to show alerts in our app.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing cancel action
        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked cancel\n operation cancel", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }

    private fun customDialogFunction() {
        val customDialog = Dialog(this)
        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customDialog.setContentView(R.layout.dialog_custom)
        customDialog.findViewById<TextView>(R.id.tv_submit).setOnClickListener {
            Toast.makeText(applicationContext, "clicked submit", Toast.LENGTH_LONG).show()
            customDialog.dismiss() // Dialog will be dismissed
        }
        customDialog.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            Toast.makeText(applicationContext, "clicked cancel", Toast.LENGTH_LONG).show()
            customDialog.dismiss()
        }
        //Start the dialog and display it on screen.
        customDialog.show()
    }

    private fun customProgressDialogFunction() {
        val customProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog.setContentView(R.layout.dialog_custom_progress)
        //customProgressDialog.setCancelable(false)

        //Start the dialog and display it on screen.
        customProgressDialog.show()
    }
}