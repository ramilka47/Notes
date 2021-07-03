package com.ramil.notes.ui.di.modules

import com.github.terrakok.cicerone.Router
import com.ramil.notes.domain.AppDatabase
import com.ramil.notes.domain.SharedPreferencesDelegate
import com.ramil.notes.ui.viewmodels.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, NavigationModule::class, SharedPreferenceModule::class])
class ViewModuleFactoryModule {

    @Provides
    @Singleton
    fun providesViewModuleFactory(database : AppDatabase,
                                  sharedPreferencesDelegate: SharedPreferencesDelegate,
                                  router: Router) : ViewModelFactory =
        ViewModelFactory(database, sharedPreferencesDelegate, router)

}