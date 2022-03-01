package com.example.cleanarchitecture.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentNoteBinding
import com.example.cleanarchitecture.framework.viewmodels.NoteViewModel
import com.example.cleanarchitecture.utilities.showToast
import com.example.core.data.Note

class NoteFragment : Fragment() {

    private val viewModel: NoteViewModel by viewModels()
    private lateinit var binding: FragmentNoteBinding
    private var currentNote = Note(title = "", content = "", creationTime = 0L, updateTime = 0L)
    private var noteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_delete_note -> {
                if (noteId != 0L) {
                    AlertDialog.Builder(context)
                        .setTitle("Delete note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes") { _, _ -> viewModel.deleteNote(currentNote) }
                        .setNegativeButton("Cancel") { _, _ ->}
                        .create().show()
                }
            }
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }
        if (noteId != 0L) viewModel.getNote(noteId)
        observeViewModel()
        setOnClickListeners()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                context.showToast("Done!")
                findNavController().popBackStack()
            } else {
                context.showToast("Something went wrong! Please try again")
            }
        }
        viewModel.currentNote.observe(viewLifecycleOwner) { note ->
            if (note != null) {
                currentNote = note
                binding.edTitle.setText(note.title, TextView.BufferType.EDITABLE)
                binding.edContent.setText(note.content, TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.fabCheck.setOnClickListener {
            if (binding.edTitle.text.isNotBlank() || binding.edContent.text.isNotBlank()) {
                currentNote.apply {
                    title = binding.edTitle.text.toString()
                    content = binding.edContent.text.toString()
                    updateTime = System.currentTimeMillis()
                    if (id == 0L) creationTime = updateTime
                }
                viewModel.saveNote(currentNote)
            } else {
                context.showToast("Please enter a title and description")
            }
        }
    }
}