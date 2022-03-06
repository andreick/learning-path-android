package com.andreick.dependencyinjection.screens.common.fragments

import androidx.fragment.app.Fragment
import com.andreick.dependencyinjection.common.composition.PresentationCompositionRoot
import com.andreick.dependencyinjection.screens.common.activities.BaseActivity

open class BaseFragment : Fragment() {

    private val activityCompositionRoot get() = (requireActivity() as BaseActivity).activityCompositionRoot
    protected val compositionRoot by lazy { PresentationCompositionRoot(activityCompositionRoot) }
}