package com.example.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coroutinesroom.databinding.FragmentSignupBinding
import com.example.coroutinesroom.util.showToast
import com.example.coroutinesroom.viewmodel.SignupViewModel

class SignupFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels()
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        observeViewModel()
    }

    private fun setOnClickListeners() {
        binding.signupBtn.setOnClickListener { onSignup() }
        binding.gotoLoginBtn.setOnClickListener { onGotoLogin() }
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner) { isComplete ->
            if (isComplete) {
                activity.showToast("SignUp complete")
                val action = SignupFragmentDirections.actionGoToMain()
                findNavController().navigate(action)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            activity.showToast("Error: $error")
        }
    }

    private fun onSignup() {
        val username = binding.signupUsername.text.toString()
        val password = binding.signupPassword.text.toString()
        val info = binding.otherInfo.text.toString()

        if (username.isEmpty() || password.isEmpty() || info.isEmpty()) {
            activity.showToast("Please fill all fields")
        } else {
            viewModel.signUp(username, password, info)
        }
    }

    private fun onGotoLogin() {
        val action = SignupFragmentDirections.actionGoToLogin()
        findNavController().navigate(action)
    }
}
