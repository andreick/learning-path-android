package com.example.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.coroutinesroom.databinding.FragmentSignupBinding
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
        binding.signupBtn.setOnClickListener { onSignup(it) }
        binding.gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner, Observer { isComplete ->

        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->

        })
    }

    private fun onSignup(v: View){
        val action = SignupFragmentDirections.actionGoToMain()
        Navigation.findNavController(v).navigate(action)
    }

    private fun onGotoLogin(v: View) {
        val action = SignupFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }
}
