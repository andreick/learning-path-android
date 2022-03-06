package com.andreick.dependencyinjection.screens.common.viewsmvc

import android.view.View

abstract class BaseViewMvc<LISTENER> {

    abstract val rootView: View

    protected val listeners = HashSet<LISTENER>()

    fun registerListener(listener: LISTENER) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER) {
        listeners.remove(listener)
    }
}