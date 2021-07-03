package com.ramil.notes.ui.di.modules

import android.content.Context
import com.bumptech.glide.Glide
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class GlideModule {

    @Provides
    @Singleton
    fun providesGlide(context: Context) = Glide.get(context)

}