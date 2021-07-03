package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.ramil.notes.domain.NoteDao
import com.ramil.notes.domain.SharedPreferencesDelegate
import java.lang.RuntimeException

class MainViewModel(private val sharedPreferencesDelegate: SharedPreferencesDelegate,
                    private val noteDao: NoteDao) : ViewModel() {

    private val token : String

    init {
        token = sharedPreferencesDelegate.getCurrentToken()?:throw RuntimeException()


    }



}