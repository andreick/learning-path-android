package com.example.cleanarchitecture.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchitecture.databinding.FragmentListBinding
import com.example.cleanarchitecture.framework.viewmodels.NoteListViewModel

class NoteListFragment : Fragment(), NoteListAction {

    private val noteAdapter = NoteListAdapter(action = this)
    private val viewModel: NoteListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeViewModel()
        binding.fabAddNote.setOnClickListener { goToNoteDetails() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }

    private fun observeViewModel() {
        viewModel.noteList.observe(viewLifecycleOwner) { noteList ->
            binding.pbLoading.visibility = View.GONE
            binding.rvNoteList.visibility = View.VISIBLE
            noteAdapter.updateNotes(noteList.sortedByDescending { it.updateTime })
        }
    }

    private fun setRecyclerView() {
        with(binding.rvNoteList) {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val action = NoteListFragmentDirections.actionAddNote(id)
        findNavController().navigate(action)
    }
}