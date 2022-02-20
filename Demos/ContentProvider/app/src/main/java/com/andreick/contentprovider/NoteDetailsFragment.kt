package com.andreick.contentprovider

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.andreick.contentprovider.database.NoteContract.NoteEntry.COLUMN_NAME_DESCRIPTION
import com.andreick.contentprovider.database.NoteContract.NoteEntry.COLUMN_NAME_TITLE
import com.andreick.contentprovider.database.NoteProvider.Companion.URI_NOTES
import com.andreick.contentprovider.databinding.NoteDetailBinding

class NoteDetailsFragment : DialogFragment(), DialogInterface.OnClickListener {

    private lateinit var binding: NoteDetailBinding
    private var id: Long = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = NoteDetailBinding.inflate(layoutInflater)
        var newNote = true
        id = arguments?.getLong(EXTRA_ID) ?: 0L
        if (id != 0L) {
            val uri = Uri.withAppendedPath(URI_NOTES, "$id")
            val cursor = activity?.run { contentResolver.query(
                uri, null, null, null, null
            ) }
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    newNote = false
                    binding.tietNoteTitle.setText(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE))
                    )
                    binding.tietNoteDescription.setText(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION))
                    )
                }
                cursor.close()
            }
        }
        return AlertDialog.Builder(activity)
            .setTitle(if (newNote) "New message" else "Edit message")
            .setView(binding.root)
            .setPositiveButton("Save", this)
            .setNegativeButton("Cancel", this)
            .create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, binding.tietNoteTitle.text.toString())
            put(COLUMN_NAME_DESCRIPTION, binding.tietNoteDescription.text.toString())
        }
        if (id == 0L) {
            context?.run { contentResolver.insert(URI_NOTES, values) }
        } else {
            val uri = Uri.withAppendedPath(URI_NOTES, "$id")
            context?.run { contentResolver.update(uri, values, null, null) }
        }
    }

    companion object {
        private const val EXTRA_ID = "id"
        fun newInstance(id: Long): NoteDetailsFragment {
            val bundle = Bundle().apply { putLong(EXTRA_ID, id) }
            return NoteDetailsFragment().apply { arguments = bundle }
        }
    }
}