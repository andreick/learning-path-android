package com.andreick.dependencyinjection.screens.common.fragments

import androidx.fragment.app.Fragment
import com.andreick.dependencyinjection.screens.common.activities.BaseActivity

open class BaseFragment : Fragment() {

    private val activityComponent get() = (requireActivity() as BaseActivity).activityComponent

    private val presentationComponent by lazy { activityComponent.newPresentationComponent() }

    protected val injector get() = presentationComponent
}