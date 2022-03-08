package com.andreick.dependencyinjection.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.MyApplication
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityComponent

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent: ActivityComponent by lazy {
        appComponent.activityComponentBuilder()
            .activity(this)
            .build()
    }

    private val presentationComponent by lazy { activityComponent.newPresentationComponent() }

    protected val injector get() = presentationComponent
}