package com.ramil.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "notes")
data class Note(@PrimaryKey(autoGenerate = true)
                val id : Long = 0,
                val title : String,
                val description : String,
                val url : String,
                val createDate : OffsetDateTime,
                val lastChangeDate : OffsetDateTime)