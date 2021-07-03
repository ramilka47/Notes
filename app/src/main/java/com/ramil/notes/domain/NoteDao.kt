package com.ramil.notes.domain

import androidx.room.*
import com.ramil.notes.data.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note : Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("select * from notes where token = :token order by datetime(createDate)")
    fun getByToken(token : String) : List<Note>

}