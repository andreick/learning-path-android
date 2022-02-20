package com.andreick.drawingapp

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var drawingView: DrawingView
    private lateinit var customProgressDialog: Dialog
    private var ibCurrentColor: ImageButton? = null

    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        permissions ->
        for ((permissionName, isGranted) in permissions.entries) {
            when (permissionName) {
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    if (isGranted) {
                        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        openGalleryLauncher.launch(pickIntent)
                    }
                    else Toast.makeText(
                        this, "Permission denied for fine location", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private val openGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == RESULT_OK) {
            val ivBackGround = findViewById<ImageView>(R.id.ivBackground)
            ivBackGround.setImageURI(result.data?.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawingView)
        val ibBrushPicker = findViewById<ImageButton>(R.id.ibBrushPicker)
        val ibGallery = findViewById<ImageButton>(R.id.ibGallery)
        val ibUndo = findViewById<ImageButton>(R.id.ibUndo)
        val ibRedo = findViewById<ImageButton>(R.id.ibRedo)
        val ibSave = findViewById<ImageButton>(R.id.ibSave)
        val llColorPallet = findViewById<LinearLayout>(R.id.llColorPallet)

        val firstColor = llColorPallet[0] as? ImageButton
        firstColor?.let { paintClicked(it) }

        drawingView.setSizeForBrush(10F)

        ibBrushPicker.setOnClickListener { showBrushSizePickerDialog() }
        ibGallery.setOnClickListener { requestStoragePermission() }
        ibUndo.setOnClickListener { drawingView.onUndo() }
        ibRedo.setOnClickListener { drawingView.onRedo() }
        ibSave.setOnClickListener {
            if (isReadStorageAllowed()) {
                showProgressDialog()
                lifecycleScope.launch {
                    val flDrawingView = findViewById<FrameLayout>(R.id.flDrawingViewContainer)
                    saveBitmapFile(getBitmapFromView(flDrawingView))
                }
            }
        }

        llColorPallet.forEach {
            val ibColor = it as? ImageButton
            ibColor?.setOnClickListener { paintClicked(ibColor) }
        }
    }

    private fun showBrushSizePickerDialog() {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size: ")

        val ibSmallBrush = brushDialog.findViewById<ImageButton>(R.id.ibSmallBrush)
        val ibMediumBrush = brushDialog.findViewById<ImageButton>(R.id.ibMediumBrush)
        val ibLargeBrush = brushDialog.findViewById<ImageButton>(R.id.ibLargeBrush)

        ibSmallBrush.setOnClickListener {
            drawingView.setSizeForBrush(10F)
            brushDialog.dismiss()
        }

        ibMediumBrush.setOnClickListener {
            drawingView.setSizeForBrush(20F)
            brushDialog.dismiss()
        }

        ibLargeBrush.setOnClickListener {
            drawingView.setSizeForBrush(30F)
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

    private fun paintClicked(ib: ImageButton) {
        if (ib !== ibCurrentColor) {
            val colorTag = ib.tag.toString()
            drawingView.setColor(colorTag)
            ib.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_selected))
            ibCurrentColor?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_normal))
            ibCurrentColor = ib
        }
    }

    private fun requestStoragePermission() {
        val storagePermissionArray = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        requestPermissions.launch(storagePermissionArray)
    }

    private fun isReadStorageAllowed(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

    private suspend fun saveBitmapFile(bitmap: Bitmap?): String {
        var result = ""
        withContext(Dispatchers.IO) {
            if (bitmap != null) {
                try {
                    val bytes = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)
                    val file = File(externalCacheDir?.absoluteFile.toString() +
                        "${File.separator}DrawingApp_${System.currentTimeMillis() / 1000}.png")
                    val fileOS = FileOutputStream(file)
                    fileOS.write(bytes.toByteArray())
                    fileOS.close()
                    result = file.absolutePath
                    runOnUiThread {
                        customProgressDialog.dismiss()
                        if (result.isNotEmpty()) {
                            Toast.makeText(this@MainActivity,
                                "File saved to $result",
                                Toast.LENGTH_SHORT
                            ).show()
                            shareImage(result)
                        }
                        else Toast.makeText(this@MainActivity,
                            "Something went wrong while saving the file",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                catch (e: Exception) {
                    customProgressDialog.dismiss()
                    e.printStackTrace()
                }
            }
        }
        return result
    }

    private fun shareImage(result: String) {
        MediaScannerConnection.scanFile(this, arrayOf(result), null) {
            path, uri ->
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.type = "image/png"
            startActivity(Intent.createChooser(shareIntent, "Share"))
        }
    }

    private fun showRationaleDialog(title: String, message: String, permissions: Array<String>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Allow") { _, _ -> requestPermissions.launch(permissions) }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun showProgressDialog() {
        customProgressDialog = Dialog(this)
        customProgressDialog.setContentView(R.layout.dialog_custom_progress)
        customProgressDialog.setCancelable(false)
        customProgressDialog.show()
    }
}