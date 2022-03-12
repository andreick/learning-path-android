package com.andreick.dependencyinjection.common.dependencyinjection.service

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule