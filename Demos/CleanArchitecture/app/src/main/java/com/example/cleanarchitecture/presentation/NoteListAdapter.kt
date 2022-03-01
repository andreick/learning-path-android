package com.example.cleanarchitecture.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.ItemNoteBinding
import com.example.cleanarchitecture.utilities.toDateFormat
import com.example.core.data.Note

class NoteListAdapter(
    private var noteList: ArrayList<Note> = arrayListOf(),
    private val action: NoteListAction
) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemNoteBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int = noteList.size

    fun updateNotes(newNotes: List<Note>) {
        noteList.clear()
        noteList.addAll(newNotes)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            with(binding) {
                tvNoteTitle.text = note.title
                tvNoteContent.text = note.content
                val updateDate = note.updateTime.toDateFormat("MMM dd, HH:mm:ss")
                tvNoteDate.text = tvNoteDate.context.getString(R.string.last_updated_date, updateDate)
                tvWordCount.text = "Words: ${note.wordCount}"
                layout.setOnClickListener { action.onClick(note.id) }
            }
        }
    }
}