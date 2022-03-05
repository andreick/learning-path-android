package com.example.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coroutinesroom.databinding.FragmentLoginBinding
import com.example.coroutinesroom.util.showToast
import com.example.coroutinesroom.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener { onLogin() }
        binding.gotoSignupBtn.setOnClickListener { onGotoSignup() }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(viewLifecycleOwner) { isComplete ->
            if (isComplete) {
                activity.showToast("Login complete")
                val action = LoginFragmentDirections.actionGoToMain()
                findNavController().navigate(action)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            activity.showToast("Error: $error")
        }
    }

    private fun onLogin() {
        val username = binding.loginUsername.text.toString()
        val password = binding.loginPassword.text.toString()
        if (username.isBlank() && password.isBlank()) {
            activity.showToast("Please fill all fields")
        } else {
            viewModel.login(username, password)
        }
    }

    private fun onGotoSignup(){
        val action = LoginFragmentDirections.actionGoToSignup()
        findNavController().navigate(action)
    }
}
