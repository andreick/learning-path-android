package com.andreick.dependencyinjection.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.MyApplication
import com.andreick.dependencyinjection.common.composition.ActivityCompositionRoot

open class BaseActivity : AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val compositionRoot by lazy { ActivityCompositionRoot(this, appCompositionRoot) }
}