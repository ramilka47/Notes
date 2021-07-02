package com.ramil.notes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.ramil.notes.domain.AppDatabase
import javax.inject.Inject

class ViewModuleFactory @Inject constructor(
    private val database : AppDatabase,
    private val router: Router) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Not yet implemented")
    }

}