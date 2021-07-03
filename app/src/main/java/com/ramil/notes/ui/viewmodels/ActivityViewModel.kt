package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.ramil.notes.domain.SharedPreferencesDelegate
import com.ramil.notes.ui.navigation.Screens

class ActivityViewModel(private val sharedPreferencesDelegate: SharedPreferencesDelegate,
                        private val router : Router) : ViewModel() {

    init {
        if (sharedPreferencesDelegate.getCurrentToken() != null){
            router.navigateTo(Screens.main())
        } else {
            router.navigateTo(Screens.login())
        }
    }

}