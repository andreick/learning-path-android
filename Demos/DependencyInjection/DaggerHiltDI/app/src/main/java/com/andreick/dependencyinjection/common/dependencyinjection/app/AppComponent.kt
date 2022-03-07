package com.andreick.dependencyinjection.common.dependencyinjection.app

import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityComponent
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
}