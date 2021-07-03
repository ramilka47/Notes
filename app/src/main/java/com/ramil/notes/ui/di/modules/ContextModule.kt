package com.ramil.notes.ui.di.modules

import android.content.Context
import com.ramil.notes.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context : Application){

    @Provides
    @Singleton
    fun providesContext() : Context = context

}