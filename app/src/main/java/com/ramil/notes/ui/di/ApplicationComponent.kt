package com.ramil.notes.ui.di

import com.ramil.notes.ui.MainActivity
import com.ramil.notes.ui.di.modules.NavigationModule
import com.ramil.notes.ui.di.modules.ViewModuleFactoryModule
import com.ramil.notes.ui.fragments.LoginFragment
import com.ramil.notes.ui.fragments.MainFragment
import com.ramil.notes.ui.fragments.NoteFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModuleFactoryModule::class, NavigationModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(loginFragment: LoginFragment)

    fun inject(mainFragment: MainFragment)

    fun inject(noteFragment: NoteFragment)

}