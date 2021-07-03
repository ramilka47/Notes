package com.ramil.notes.ui.di.modules

import com.ramil.notes.domain.db.AppDatabase
import com.ramil.notes.domain.SharedPreferencesDelegate
import com.ramil.notes.ui.viewmodels.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, SharedPreferenceModule::class])
class ViewModuleFactoryModule {

    @Provides
    @Singleton
    fun providesViewModuleFactory(database : AppDatabase,
                                  sharedPreferencesDelegate: SharedPreferencesDelegate) : ViewModelFactory =
        ViewModelFactory(database, sharedPreferencesDelegate)

}