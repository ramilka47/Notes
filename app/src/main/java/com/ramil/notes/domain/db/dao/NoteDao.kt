package com.ramil.notes.domain.db.dao

import androidx.room.*
import com.ramil.notes.data.entity.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note : Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("select * from notes where id = :id")
    suspend fun getById(id : Long) : Note?

    @Query("select * from notes where token = :token order by datetime(createDate)")
    suspend fun getByTokenDone(token : String) : List<Note>

    @Query("select * from notes where token = :token and done = 1 order by datetime(createDate)")
    suspend fun getByToken(token : String) : List<Note>

    @Query("select * from notes where token = :token and done = 0 order by datetime(createDate)")
    suspend fun getByTokenOpen(token : String) : List<Note>

}