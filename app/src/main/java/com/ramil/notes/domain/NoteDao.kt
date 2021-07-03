package com.ramil.notes.domain

import androidx.room.*
import com.ramil.notes.data.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note : Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("select * from notes where token = :token order by datetime(createDate)")
    suspend fun getByToken(token : String) : List<Note>

}