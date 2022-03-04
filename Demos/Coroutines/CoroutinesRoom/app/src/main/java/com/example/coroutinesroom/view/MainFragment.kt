package com.example.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.coroutinesroom.databinding.FragmentMainBinding
import com.example.coroutinesroom.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signoutBtn.setOnClickListener { onSignout() }
        binding.deleteUserBtn.setOnClickListener { onDelete() }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signOut.observe(this, Observer {

        })
        viewModel.userDeleted.observe(this, Observer {

        })
    }

    private fun onSignout() {
        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(binding.usernameTV).navigate(action)
    }

    private fun onDelete() {
        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(binding.usernameTV).navigate(action)
    }
}
