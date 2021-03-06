package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramil.notes.data.Action
import com.ramil.notes.data.entity.Note
import com.ramil.notes.domain.SharedPreferencesDelegate
import com.ramil.notes.domain.db.dao.NoteDao
import kotlinx.coroutines.*
import java.lang.RuntimeException

class NoteViewModel(private val noteDao: NoteDao,
                    private val sharedPreferencesDelegate: SharedPreferencesDelegate) : ViewModel() {

    private val coroutineIO = CoroutineScope(Dispatchers.IO)

    private var currentNote : Note? = null

    private val doneLiveData = MutableLiveData<Boolean>()
    val done : LiveData<Boolean> = doneLiveData

    private val markOfAcceptChangedLiveData = MutableLiveData<Boolean>()
    val markOfAcceptChanged : LiveData<Boolean> = markOfAcceptChangedLiveData

    private val noteLiveData = MutableLiveData<Note>()
    val note : LiveData<Note> = noteLiveData

    private val doseNotNoteLiveData = MutableLiveData<Boolean>()
    val doseNotNote : LiveData<Boolean> = doseNotNoteLiveData

    private val arrayOfActionLiveData = MutableLiveData<Array<Action>>()
    val arrayOfAction : LiveData<Array<Action>> = arrayOfActionLiveData

    fun getNote(id : Long?){
        coroutineIO.launch {
            if (id != null) {
                val note = getNoteById(id)
                if (note == null) {
                    currentNote = Note(
                        token = sharedPreferencesDelegate.getCurrentToken()
                            ?: throw RuntimeException()
                    )
                    doseNotNoteLiveData.postValue(true)
                } else {
                    currentNote = note
                    noteLiveData.postValue(note)
                }
            } else {
                currentNote = Note(
                    token = sharedPreferencesDelegate.getCurrentToken()
                        ?: throw RuntimeException()
                )
                doseNotNoteLiveData.postValue(true)
            }
            updateArrayOfAction()
        }
    }

    private fun updateArrayOfAction(){
        val list = mutableListOf(
            if (currentNote?.url == null){
                Action.AddImage
            } else
                Action.DeleteImage,
            if (currentNote?.done == true){
                Action.ClearMarkOfFinal
            } else {
                Action.MarkOfFinal
            })
        if (currentNote?.id != 0L){
            list.add(Action.DeleteNote)
        }
        arrayOfActionLiveData.postValue(list.toTypedArray())
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

    fun addImage(url : String){
        markOfAcceptChangedLiveData.postValue(true)
        currentNote?.let {
            currentNote = Note(it, url = url)
        }
    }

    fun deleteImage(){
        markOfAcceptChangedLiveData.postValue(true)
        currentNote?.let {
            currentNote = Note(it, url = null)
        }
    }

    fun deleteNote(){
        coroutineIO.launch {
            noteDao.delete(currentNote ?: return@launch)
            doneLiveData.postValue(true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineIO.cancel()
    }

    private suspend fun updateNote(note: Note){
        if (note.id != 0L)
            noteDao.update(Note(note))
        else
            noteDao.insert(note)
        doneLiveData.postValue(true)
    }

    private suspend fun getNoteById(id : Long) = noteDao.getById(id)

    private suspend fun deleteNote(note : Note){
        noteDao.delete(note)
    }

}