package com.andreick.dependencyinjection.common.dependencyinjection.presentation

import androidx.lifecycle.ViewModel
import com.andreick.dependencyinjection.common.dependencyinjection.ViewModelKey
import com.andreick.dependencyinjection.screens.viewmodel.MyOtherViewModel
import com.andreick.dependencyinjection.screens.viewmodel.MyViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyViewModel::class)
    abstract fun myViewModel(myViewModel: MyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyOtherViewModel::class)
    abstract fun myOtherViewModel(myOtherViewModel: MyOtherViewModel): ViewModel
}