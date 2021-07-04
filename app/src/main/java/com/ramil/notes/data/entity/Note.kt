package com.ramil.notes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "notes")
class Note{

    @PrimaryKey(autoGenerate = true)
    val id : Long
    val title : String
    val description : String
    val url : String?
    val createDate : OffsetDateTime
    val lastChangeDate : OffsetDateTime
    val done : Boolean
    val token : String

    constructor(id : Long = 0,
                title : String = "",
                description : String = "",
                url : String? = null,
                done : Boolean = false,
                token : String){
        this.id = id
        this.title = title
        this.description = description
        this.url = url
        this.createDate = OffsetDateTime.now()
        this.lastChangeDate = OffsetDateTime.now()
        this.done = done
        this.token = token
    }

    constructor(note: Note,
                title: String? = null,
                description: String? = null,
                url : String? = null,
                done: Boolean? = null){
        this.id = note.id
        this.title = title ?: note.title
        this.description = description ?: note.description
        this.url = url ?: note.url
        this.createDate = note.createDate
        this.token = note.token
        this.done = done ?: note.done
        this.lastChangeDate = OffsetDateTime.now()
    }

}