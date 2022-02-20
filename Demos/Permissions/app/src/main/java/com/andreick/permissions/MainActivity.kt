package com.andreick.permissions

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted ->
        if (isGranted) Toast.makeText(this, "Permission granted for camera", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this, "Permission denied for camera", Toast.LENGTH_SHORT).show()
    }

    private val cameraAndLocationResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissions ->
        for ((permissionName, isGranted) in permissions.entries) {
            when (permissionName) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if (isGranted) Toast.makeText(
                        this, "Permission granted for fine location", Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(
                        this, "Permission denied for fine location", Toast.LENGTH_SHORT
                    ).show()
                }
                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    if (isGranted) Toast.makeText(
                        this, "Permission granted for coarse location", Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(
                        this, "Permission denied for coarse location", Toast.LENGTH_SHORT
                    ).show()
                }
                Manifest.permission.CAMERA -> {
                    if (isGranted) Toast.makeText(
                        this, "Permission granted for camera", Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(
                        this, "Permission denied for camera", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRequestCameraPermission = findViewById<Button>(R.id.btnRequestCameraPermission)
        btnRequestCameraPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showRationaleDialog("This app requires camera access",
                "Camera cannot be used because Camera access is denied")
            }
            else {
                //cameraResultLauncher.launch(Manifest.permission.CAMERA)
                cameraAndLocationResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }
    }

    private fun showRationaleDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Allow") { _, _ ->
                cameraAndLocationResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}