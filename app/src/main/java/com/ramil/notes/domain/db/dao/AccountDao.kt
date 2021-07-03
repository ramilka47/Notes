package com.ramil.notes.domain.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ramil.notes.data.Account

@Dao
interface AccountDao {

    @Insert
    suspend fun insert(account : Account)

    @Query("Select * from accounts where login = :login")
    suspend fun getByLoginPassword(login : String) : Account?

}