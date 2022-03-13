package com.example.cleanarchitecture.presentation

import androidx.fragment.app.Fragment
import com.example.cleanarchitecture.MyApplication

abstract class BaseFragment : Fragment() {

    protected val injector get() = (requireActivity().application as MyApplication)
        .applicationComponent
        .createFragmentComponent()
}