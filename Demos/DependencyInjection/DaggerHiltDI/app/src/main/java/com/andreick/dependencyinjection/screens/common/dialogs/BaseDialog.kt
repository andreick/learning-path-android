package com.andreick.dependencyinjection.screens.common.dialogs

import androidx.fragment.app.DialogFragment
import com.andreick.dependencyinjection.common.dependencyinjection.presentation.PresentationModule
import com.andreick.dependencyinjection.screens.common.activities.BaseActivity

open class BaseDialog : DialogFragment() {

    private val activityComponent get() = (requireActivity() as BaseActivity).activityComponent

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule(this))
    }
}