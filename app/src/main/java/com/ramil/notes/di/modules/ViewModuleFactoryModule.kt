package com.ramil.notes.di.modules

import com.github.terrakok.cicerone.Router
import com.ramil.notes.domain.AppDatabase
import com.ramil.notes.viewmodels.ViewModuleFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, NavigationModule::class])
class ViewModuleFactoryModule {

    @Provides
    @Singleton
    fun providesViewModuleFactory(database : AppDatabase, router: Router) : ViewModuleFactory =
        ViewModuleFactory(database, router)

}