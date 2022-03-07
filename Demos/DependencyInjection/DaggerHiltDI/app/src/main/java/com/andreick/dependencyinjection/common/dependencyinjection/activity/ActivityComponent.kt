package com.andreick.dependencyinjection.common.dependencyinjection.activity

import com.andreick.dependencyinjection.common.dependencyinjection.presentation.PresentationComponent
import com.andreick.dependencyinjection.common.dependencyinjection.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent
}