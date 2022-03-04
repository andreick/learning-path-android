package com.example.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.coroutinesroom.databinding.FragmentLoginBinding
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

        binding.loginBtn.setOnClickListener { onLogin(it) }
        binding.gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(viewLifecycleOwner, Observer { isComplete ->
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
        })
    }

    private fun onLogin(v: View) {
        val action = LoginFragmentDirections.actionGoToMain()
        Navigation.findNavController(v).navigate(action)
    }

    private fun onGotoSignup(v: View){
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }
}
