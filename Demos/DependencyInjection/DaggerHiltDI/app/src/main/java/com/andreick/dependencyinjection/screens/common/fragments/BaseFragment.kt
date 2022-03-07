package com.andreick.dependencyinjection.screens.common.fragments

import androidx.fragment.app.Fragment
import com.andreick.dependencyinjection.common.dependencyinjection.Injector
import com.andreick.dependencyinjection.common.dependencyinjection.PresentationCompositionRoot
import com.andreick.dependencyinjection.screens.common.activities.BaseActivity

open class BaseFragment : Fragment() {

    private val activityCompositionRoot get() = (requireActivity() as BaseActivity).activityCompositionRoot
    private val compositionRoot by lazy { PresentationCompositionRoot(activityCompositionRoot) }

    protected val injector get() = Injector(compositionRoot)
}