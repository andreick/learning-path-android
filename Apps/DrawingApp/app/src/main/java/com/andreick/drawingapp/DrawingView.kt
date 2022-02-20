package com.andreick.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private lateinit var canvasBitmap: Bitmap
    private lateinit var canvas: Canvas

    private var drawPath: CustomPath
    private var drawPaint: Paint
    private var canvasPaint: Paint
    private var color = Color.BLACK
    private var brushSize = 0F

    private val paths = ArrayList<CustomPath>()
    private val redoPaths = ArrayList<CustomPath>()

    init {
        drawPath = CustomPath(color, brushSize)
        drawPaint = Paint()
        drawPaint.color = color
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(canvasBitmap, 0f, 0f, canvasPaint)

        for (path in paths) {
            drawPaint.strokeWidth = path.brushThickness
            drawPaint.color = path.color
            canvas?.drawPath(path, drawPaint)
        }

        if (!drawPath.isEmpty) {
            drawPaint.strokeWidth = drawPath.brushThickness
            drawPaint.color = drawPath.color
            canvas?.drawPath(drawPath, drawPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPath.color = color
                drawPath.brushThickness = brushSize

                drawPath.reset()
                drawPath.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                drawPath.lineTo(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                paths.add(drawPath)
                redoPaths.clear()
                drawPath = CustomPath(color, brushSize)
            }
            else -> return false
        }

        invalidate()
        return true
    }

    fun onUndo() {
        if (paths.isNotEmpty()) {
            redoPaths.add(paths.removeLast())
            invalidate()
        }
    }

    fun onRedo() {
        if (redoPaths.isNotEmpty()) {
            paths.add(redoPaths.removeLast())
            invalidate()
        }
    }

    fun setSizeForBrush(newSize: Float) {
        brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics)
        drawPaint.strokeWidth = brushSize
    }

    fun setColor(newColor: String) {
        color = Color.parseColor(newColor)
        drawPaint.color = color
    }

    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path() {

    }
}