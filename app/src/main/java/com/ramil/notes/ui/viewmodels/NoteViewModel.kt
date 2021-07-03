package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramil.notes.data.Note
import com.ramil.notes.domain.NoteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {

    private val coroutineIO = CoroutineScope(Dispatchers.IO)

    private val addedLiveData = MutableLiveData<Boolean>()
    val added : LiveData<Boolean> = addedLiveData

    private val noteLiveData = MutableLiveData<Note>()
    val note : LiveData<Note> = noteLiveData

    private val doseNotNoteLiveData = MutableLiveData<Boolean>()
    val doseNotNote : LiveData<Boolean> = doseNotNoteLiveData

    fun getNote(id : Long){
        coroutineIO.launch {
            val note = getNoteById(id)
            if (note == null){
                doseNotNoteLiveData.postValue(true)
            } else {
                noteLiveData.postValue(note)
            }
        }
    }

    fun markOfFinal(){

    }

    fun clearMarkOfFinal(){

    }

    fun deleteNote(){

    }

    fun addImage(){

    }

    fun deleteImage(){

    }

    override fun onCleared() {
        super.onCleared()
        coroutineIO.cancel()
    }

    private suspend fun addNote(note : Note){
        noteDao.insert(note)
    }

    private suspend fun updateNote(note: Note){
        noteDao.update(note)
    }

    private suspend fun getNoteById(id : Long) = noteDao.getById(id)

    private suspend fun deleteNote(note : Note){
        noteDao.delete(note)
    }

}