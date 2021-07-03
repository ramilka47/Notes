package com.ramil.notes.ui.di

import com.ramil.notes.ui.MainActivity
import com.ramil.notes.ui.di.modules.NavigationModule
import com.ramil.notes.ui.di.modules.ViewModuleFactoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModuleFactoryModule::class, NavigationModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

}