package com.andreick.dependencyinjection.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.MyApplication
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityComponent
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityModule
import com.andreick.dependencyinjection.common.dependencyinjection.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent: ActivityComponent by lazy {
        appComponent.newActivityComponent(ActivityModule(this))
    }

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule())
    }

    protected val injector get() = presentationComponent
}