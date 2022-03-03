package com.andreick.contentprovider

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns._ID
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreick.contentprovider.database.NoteContract.NoteEntry.COLUMN_NAME_TITLE
import com.andreick.contentprovider.database.NoteProvider.Companion.URI_NOTES
import com.andreick.contentprovider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor>, NoteOnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initLoader()
        setupOnClick()
    }

    private fun initRecyclerView() {
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        adapter = NotesAdapter(this).apply { setHasStableIds(true) }
        binding.rvNotes.adapter = adapter
    }

    override fun onNoteClickItem(cursor: Cursor) {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID))
        val fragment = NoteDetailsFragment.newInstance(id)
        fragment.show(supportFragmentManager, "dialog")
    }

    override fun onNoteRemoveItem(cursor: Cursor) {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID))
        contentResolver.delete(
            Uri.withAppendedPath(URI_NOTES, "$id"),
            null, null
        )
    }

    private fun initLoader() {
        LoaderManager.getInstance(this).initLoader(0, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            this, URI_NOTES, null,
            null, null, COLUMN_NAME_TITLE
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null) adapter.setCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.setCursor(null)
    }

    private fun setupOnClick() {
        binding.fabAdd.setOnClickListener { NoteDetailsFragment().show(supportFragmentManager, "dialog") }
    }
}