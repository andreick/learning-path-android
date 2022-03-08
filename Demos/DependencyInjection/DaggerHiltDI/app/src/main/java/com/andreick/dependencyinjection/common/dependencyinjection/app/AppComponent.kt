package com.andreick.dependencyinjection.common.dependencyinjection.app

import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityComponent
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityModule
import com.andreick.dependencyinjection.common.dependencyinjection.service.ServiceComponent
import com.andreick.dependencyinjection.common.dependencyinjection.service.ServiceModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
    fun newServiceComponent(serviceModule: ServiceModule): ServiceComponent
}