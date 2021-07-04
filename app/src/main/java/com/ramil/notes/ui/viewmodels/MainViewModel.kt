package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramil.notes.data.entity.Note
import com.ramil.notes.domain.db.dao.NoteDao
import com.ramil.notes.domain.SharedPreferencesDelegate
import kotlinx.coroutines.*
import java.lang.RuntimeException

class MainViewModel(private val noteDao: NoteDao,
                    private val sharedPreferencesDelegate: SharedPreferencesDelegate) : ViewModel() {

    private val token : String = sharedPreferencesDelegate.getCurrentToken() ?: throw RuntimeException()

    private val coroutineIO = CoroutineScope(Dispatchers.IO)

    private val loadingLiveData = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = loadingLiveData

    private val emptyListLiveData = MutableLiveData<Boolean>()
    val empty : LiveData<Boolean> = emptyListLiveData

    private val noteListLiveData = MutableLiveData<List<Note>>()
    val noteList : LiveData<List<Note>> = noteListLiveData

    private val doneListLiveData = MutableLiveData<List<Note>>()
    val doneList : LiveData<List<Note>> = doneListLiveData

    init {
        getNotes()
    }

    fun getNotes(){
        coroutineIO.launch {
            loadingLiveData.postValue(true)
            val open = getNotesOpen()
            val done = getNotesDone()

            if (open.isEmpty() && done.isEmpty()){
                emptyListLiveData.postValueWithResetLoading(true)
                return@launch
            }

            noteListLiveData.postValueWithResetLoading(open)
            doneListLiveData.postValueWithResetLoading(done)
        }
    }

    override fun onCleared() {
        coroutineIO.cancel()
        super.onCleared()
    }

    private suspend fun getNotesDone() = noteDao.getByTokenDone(token)

    private suspend fun getNotesOpen() = noteDao.getByTokenOpen(token)

    private fun <T> MutableLiveData<T>.postValueWithResetLoading(t : T){
        loadingLiveData.postValue(false)
        this.postValue(t)
    }
}