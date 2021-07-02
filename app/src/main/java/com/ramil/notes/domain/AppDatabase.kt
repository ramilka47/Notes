package com.ramil.notes.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ramil.notes.data.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(DateTypeConverters::class)
abstract class AppDatabase : RoomDatabase(){
}