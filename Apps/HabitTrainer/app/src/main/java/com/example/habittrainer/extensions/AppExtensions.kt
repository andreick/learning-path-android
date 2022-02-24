package com.example.habittrainer.extensions

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.core.database.getBlobOrNull
import androidx.core.database.getStringOrNull
import java.io.ByteArrayOutputStream

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 0, stream)
    return stream.toByteArray()
}

inline fun<T> SQLiteDatabase.doTransaction(operation: SQLiteDatabase.() -> T): T {
    beginTransaction()
    return try {
        operation().also { setTransactionSuccessful() }
    } finally {
        endTransaction()
        close()
    }
}

fun SQLiteDatabase.doQuery(
    table: String, columns: Array<String>,
    selection: String? = null, selectionArgs: Array<String>? = null,
    groupBy: String? = null, having: String? = null, orderBy: String? = null
): Cursor {
    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
}

fun Cursor.getString(columnName: String): String = getString(getColumnIndexOrThrow(columnName))

fun Cursor.getStringOrNull(columnName: String) = getStringOrNull(getColumnIndex(columnName))

fun Cursor.getBitMap(columnName: String): Bitmap {
    val byteArray = getBlob(getColumnIndexOrThrow(columnName))
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun Cursor.getBitMapOrNull(columnName: String): Bitmap? {
    val byteArray = getBlobOrNull(getColumnIndex(columnName))
    return byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size) }
}