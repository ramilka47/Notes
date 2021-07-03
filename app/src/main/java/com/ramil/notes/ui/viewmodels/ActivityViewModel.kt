package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramil.notes.domain.SharedPreferencesDelegate

class ActivityViewModel(private val sharedPreferencesDelegate: SharedPreferencesDelegate) : ViewModel() {

    private val screenMain = MutableLiveData<Boolean>()
    val main : LiveData<Boolean> = screenMain

    private val screenLogin = MutableLiveData<Boolean>()
    val login : LiveData<Boolean> = screenLogin

    init {
        if (sharedPreferencesDelegate.getCurrentToken() != null){
            screenMain.postValue(true)
        } else {
            screenLogin.postValue(true)
        }
    }

}