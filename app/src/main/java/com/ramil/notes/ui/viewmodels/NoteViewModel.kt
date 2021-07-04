package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramil.notes.data.entity.Note
import com.ramil.notes.domain.SharedPreferencesDelegate
import com.ramil.notes.domain.db.dao.NoteDao
import kotlinx.coroutines.*
import java.lang.RuntimeException

class NoteViewModel(private val noteDao: NoteDao,
                    private val sharedPreferencesDelegate: SharedPreferencesDelegate) : ViewModel() {

    private val coroutineIO = CoroutineScope(Dispatchers.IO)

    private var currentNote : Note? = null

    private val addedLiveData = MutableLiveData<Boolean>()
    val added : LiveData<Boolean> = addedLiveData

    private val noteLiveData = MutableLiveData<Note>()
    val note : LiveData<Note> = noteLiveData

    private val doseNotNoteLiveData = MutableLiveData<Boolean>()
    val doseNotNote : LiveData<Boolean> = doseNotNoteLiveData

    private val markOfAcceptChangedLiveData = MutableLiveData<Boolean>()
    val markOfAcceptChanged : LiveData<Boolean> = markOfAcceptChangedLiveData

    fun getNote(id : Long){
        coroutineIO.launch {
            val note = getNoteById(id)
            if (note == null){
                currentNote = Note(token = sharedPreferencesDelegate.getCurrentToken() ?: throw RuntimeException())
                doseNotNoteLiveData.postValue(true)
            } else {
                currentNote = note
                noteLiveData.postValue(note)
            }
        }
    }

    fun acceptChanged(){
        coroutineIO.launch {
            currentNote?.let {
                updateNote(it)
            }
        }
    }

    fun titleChanged(title : String){
        markOfAcceptChangedLiveData.postValue(true)
        currentNote?.let {
            currentNote = Note(it, title = title)
        }
    }

    fun descriptionChanged(description : String){
        markOfAcceptChangedLiveData.postValue(true)
        currentNote?.let {
            currentNote = Note(it, description = description)
        }
    }

    fun markOfFinal(){
        markOfAcceptChangedLiveData.postValue(true)
        currentNote?.let {
            currentNote = Note(it, done = true)
        }
    }

    fun clearMarkOfFinal(){
        markOfAcceptChangedLiveData.postValue(true)
        currentNote?.let {
            currentNote = Note(it, done = false)
        }
    }

    fun addImage(){
        markOfAcceptChangedLiveData.postValue(true)

    }

    fun deleteImage(){
        markOfAcceptChangedLiveData.postValue(true)
        currentNote?.let {
            currentNote = Note(it, url = null)
        }
    }

    fun deleteNote(){

    }

    override fun onCleared() {
        super.onCleared()
        coroutineIO.cancel()
    }

    private suspend fun addNote(note : Note){
        noteDao.insert(note)
    }

    private suspend fun updateNote(note: Note){
        noteDao.update(Note(note))
    }

    private suspend fun getNoteById(id : Long) = noteDao.getById(id)

    private suspend fun deleteNote(note : Note){
        noteDao.delete(note)
    }

}