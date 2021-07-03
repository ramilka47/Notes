package com.ramil.notes.ui.di.modules

import android.content.Context
import androidx.room.Room
import com.ramil.notes.domain.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context : Context) : AppDatabase =
        Room.databaseBuilder(context,
            AppDatabase::class.java, "notes")
            .build()

}