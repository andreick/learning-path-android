package com.example.coroutinesroom.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coroutinesroom.databinding.FragmentMainBinding
import com.example.coroutinesroom.model.LoginState
import com.example.coroutinesroom.util.showToast
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

        binding.usernameTV.text = LoginState.user?.username
        binding.signoutBtn.setOnClickListener { onSignOut() }
        binding.deleteUserBtn.setOnClickListener { onDelete() }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signOut.observe(viewLifecycleOwner) { signedOut ->
            if (signedOut) {
                activity.showToast("Signed out")
                goToSignup()
            }
        }
        viewModel.userDeleted.observe(viewLifecycleOwner) { userDeleted ->
            if (userDeleted) {
                activity.showToast("User deleted")
                goToSignup()
            }
        }
    }

    private fun goToSignup() {
        val action = MainFragmentDirections.actionGoToSignup()
        findNavController().navigate(action)
    }

    private fun onSignOut() {
        viewModel.onSignOut()
    }

    private fun onDelete() {
        activity.let {
            AlertDialog.Builder(it)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes") { _, _ -> viewModel.onDeleteUser() }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }
}
