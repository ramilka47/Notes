package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramil.notes.data.Note
import com.ramil.notes.domain.db.dao.NoteDao
import com.ramil.notes.domain.SharedPreferencesDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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

    override fun onCleared() {
        coroutineIO.cancel()
        super.onCleared()
    }

    private suspend fun getNotes() = noteDao.getByToken(token)

}