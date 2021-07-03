package com.ramil.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(@PrimaryKey(autoGenerate = true)
                   val id : Long = 0,
                   val login : String,
                   val password : String,
                   val token : String)