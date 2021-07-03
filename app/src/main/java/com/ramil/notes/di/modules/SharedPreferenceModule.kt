package com.ramil.notes.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.ramil.notes.domain.SharedPreferencesDelegate
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun providePreferences(context : Context) = context.getSharedPreferences("endiv", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideDelegatePreferences(sharedPreferences: SharedPreferences) = SharedPreferencesDelegate(sharedPreferences)

}