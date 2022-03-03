package com.andreick.contentprovider

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreick.contentprovider.database.NoteContract.NoteEntry.COLUMN_NAME_DESCRIPTION
import com.andreick.contentprovider.database.NoteContract.NoteEntry.COLUMN_NAME_TITLE
import com.andreick.contentprovider.databinding.ItemNoteBinding

class NotesAdapter(
    private val listener: NoteOnClickListener
) : RecyclerView.Adapter<NotesViewHolder>() {

    private var cursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        cursor?.let { cursor ->
            cursor.moveToPosition(position)
            holder.binding.tvNoteTitle.text =
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE))
            holder.binding.tvNoteDescription.text =
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION))
            holder.binding.btnNoteRemove.setOnClickListener {
                cursor.moveToPosition(position)
                listener.onNoteRemoveItem(cursor)
                notifyItemChanged(position)
            }
            holder.itemView.setOnClickListener { listener.onNoteClickItem(cursor) }
        }
    }

    override fun getItemCount(): Int = cursor?.count ?: 0

    fun setCursor(newCursor: Cursor?) {
        cursor = newCursor
        notifyDataSetChanged()
    }
}

class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)
