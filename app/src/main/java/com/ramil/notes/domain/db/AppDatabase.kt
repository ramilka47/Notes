package com.ramil.notes.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ramil.notes.data.entity.Account
import com.ramil.notes.data.entity.Note
import com.ramil.notes.domain.db.dao.AccountDao
import com.ramil.notes.domain.db.dao.NoteDao

@Database(entities = [Note::class, Account::class], version = 1)
@TypeConverters(DateTypeConverters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun noteDao() : NoteDao

    abstract fun accountDao() : AccountDao

}