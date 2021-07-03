package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.ramil.notes.domain.AppDatabase
import com.ramil.notes.domain.SharedPreferencesDelegate
import java.lang.Exception
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val database : AppDatabase,
    private val sharedPreferencesDelegate: SharedPreferencesDelegate) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when(modelClass){
            AuthViewModel::class.java->{
                AuthViewModel(database.accountDao(), sharedPreferencesDelegate)
            }
            NoteViewModel::class.java->{
                NoteViewModel()
            }
            MainViewModel::class.java->{
                MainViewModel(database.noteDao(), sharedPreferencesDelegate)
            }
            ActivityViewModel::class.java->{
                ActivityViewModel(sharedPreferencesDelegate)
            }
            else->{
                throw Exception()
            }
        } as T

}