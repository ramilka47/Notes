package com.ramil.notes.di

import com.ramil.notes.di.modules.ViewModuleFactoryModule
import dagger.Component

@Component(modules = [ViewModuleFactoryModule::class])
interface ApplicationComponent {
}