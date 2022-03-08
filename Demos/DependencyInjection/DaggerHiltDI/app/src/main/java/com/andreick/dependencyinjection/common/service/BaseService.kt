package com.andreick.dependencyinjection.common.service

import android.app.Service
import com.andreick.dependencyinjection.MyApplication
import com.andreick.dependencyinjection.common.dependencyinjection.service.ServiceComponent
import com.andreick.dependencyinjection.common.dependencyinjection.service.ServiceModule

abstract class BaseService : Service() {

    private val appComponent get() = (application as MyApplication).appComponent

    val serviceComponent: ServiceComponent by lazy {
        appComponent.newServiceComponent(ServiceModule(this))
    }
}