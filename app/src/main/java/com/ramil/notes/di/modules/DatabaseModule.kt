package com.ramil.notes.di.modules

import android.content.Context
import androidx.room.Room
import com.ramil.notes.domain.DateTypeConverters
import com.ramil.notes.domain.AppDatabase
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