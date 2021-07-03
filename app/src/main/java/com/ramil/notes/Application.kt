package com.ramil.notes

import android.app.Application
import com.ramil.notes.ui.di.ApplicationComponent
import com.ramil.notes.ui.di.DaggerApplicationComponent
import com.ramil.notes.ui.di.modules.ContextModule

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object{
        private lateinit var applicationComponent: ApplicationComponent

        val component : ApplicationComponent
            get() = applicationComponent
    }

}