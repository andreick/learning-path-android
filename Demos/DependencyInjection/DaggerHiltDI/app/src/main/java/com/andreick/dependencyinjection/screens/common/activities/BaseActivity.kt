package com.andreick.dependencyinjection.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.MyApplication
import com.andreick.dependencyinjection.common.dependencyinjection.*
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityComponent
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityModule
import com.andreick.dependencyinjection.common.dependencyinjection.activity.DaggerActivityComponent
import com.andreick.dependencyinjection.common.dependencyinjection.presentation.DaggerPresentationComponent
import com.andreick.dependencyinjection.common.dependencyinjection.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {

    private val appComponent get()= (application as MyApplication).appComponent

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this, appComponent))
            .build()
    }

    private val presentationComponent by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule(activityComponent))
            .build()
    }

    protected val injector get() = Injector(presentationComponent)
}