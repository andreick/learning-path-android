package com.andreick.dependencyinjection.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.MyApplication
import com.andreick.dependencyinjection.common.dependencyinjection.ActivityCompositionRoot
import com.andreick.dependencyinjection.common.dependencyinjection.Injector
import com.andreick.dependencyinjection.common.dependencyinjection.PresentationCompositionRoot

open class BaseActivity : AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val activityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    private val compositionRoot by lazy { PresentationCompositionRoot(activityCompositionRoot) }

    protected val injector get() = Injector(compositionRoot)
}