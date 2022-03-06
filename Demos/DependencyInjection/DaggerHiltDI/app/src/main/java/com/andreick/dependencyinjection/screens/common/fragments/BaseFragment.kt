package com.andreick.dependencyinjection.screens.common.fragments

import androidx.fragment.app.Fragment
import com.andreick.dependencyinjection.screens.common.activities.BaseActivity

open class BaseFragment : Fragment() {

    protected val compositionRoot get() = (requireActivity() as BaseActivity).compositionRoot
}