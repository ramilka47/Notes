package com.ramil.notes.domain

import android.content.SharedPreferences

class SharedPreferencesDelegate(private val sharedPreferences : SharedPreferences) {

    companion object{
        private const val TOKEN = "Token"
    }

    fun getCurrentToken() : String? = sharedPreferences.getString(TOKEN, null)

    fun setToken(token : String) = sharedPreferences.edit().putString(TOKEN, token).commit()

}