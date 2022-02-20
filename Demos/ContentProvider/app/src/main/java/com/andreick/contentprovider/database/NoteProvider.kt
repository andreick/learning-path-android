package com.andreick.contentprovider.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.provider.BaseColumns._ID
import com.andreick.contentprovider.database.NoteContract.NoteEntry.TABLE_NAME

class NoteProvider : ContentProvider() {

    private lateinit var uriMatcher: UriMatcher
    private lateinit var dbHelper: NoteDbHelper

    override fun onCreate(): Boolean {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, NOTES_PATH_SEGMENT, NOTES)
            addURI(AUTHORITY, NOTES_BY_ID_PATH_SEGMENT, NOTES_BY_ID)
        }
        context?.let { context -> dbHelper = NoteDbHelper(context) }
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        if (uriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val numOfRows = db.delete(TABLE_NAME, "$_ID = ?", arrayOf(uri.lastPathSegment))
            db.close()
            context?.run { contentResolver.notifyChange(uri, null) }
            return numOfRows
        } else {
            throw UnsupportedSchemeException("Invalid delete URI")
        }
    }

    override fun getType(uri: Uri): String? = throw UnsupportedSchemeException("Not implemented URI")

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (uriMatcher.match(uri) == NOTES) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id = db.insert(TABLE_NAME, null, values)
            db.close()
            context?.run { contentResolver.notifyChange(uri, null) }
            return Uri.withAppendedPath(BASE_URI, "$id")
        } else {
            throw UnsupportedSchemeException("Invalid insert URI")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            NOTES -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                db.query(
                    TABLE_NAME, projection, selection, selectionArgs,
                    null, null, sortOrder
                ).apply { setNotificationUri(context?.contentResolver, uri) }
            }
            NOTES_BY_ID -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                db.query(
                    TABLE_NAME, projection, "$_ID = ?", arrayOf(uri.lastPathSegment),
                    null, null, sortOrder
                ).apply { setNotificationUri(context?.contentResolver, uri) }
            }
            else -> throw UnsupportedSchemeException("Not implemented URI")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (uriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val numOfRows = db.update(TABLE_NAME, values, "$_ID = ?", arrayOf(uri.lastPathSegment))
            db.close()
            context?.run { contentResolver.notifyChange(uri, null) }
            return numOfRows
        } else {
            throw UnsupportedSchemeException("Invalid update URI")
        }
    }

    companion object {
        const val AUTHORITY = "com.andreick.contentprovider.provider"
        const val NOTES = 1
        const val NOTES_BY_ID = 2

        private const val NOTES_PATH_SEGMENT = "notes"
        private const val NOTES_BY_ID_PATH_SEGMENT = "notes/#"

        val BASE_URI = Uri.parse("content://$AUTHORITY")
        val URI_NOTES = Uri.withAppendedPath(BASE_URI, NOTES_PATH_SEGMENT)
    }
}