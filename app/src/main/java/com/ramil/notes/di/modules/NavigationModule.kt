package com.ramil.notes.di.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone : Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun providesRouter(): Router = cicerone.router

    @Singleton
    @Provides
    fun providesNavigationHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

}