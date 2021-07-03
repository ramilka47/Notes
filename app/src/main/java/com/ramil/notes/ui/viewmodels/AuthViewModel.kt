package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramil.notes.data.Account
import com.ramil.notes.domain.db.dao.AccountDao
import com.ramil.notes.domain.SharedPreferencesDelegate
import kotlinx.coroutines.*

class AuthViewModel(private val accountDao: AccountDao,
                    private val sharedPreferencesDelegate: SharedPreferencesDelegate) : ViewModel() {

    private var auth : Job? = null
    private val coroutineIO = CoroutineScope(Dispatchers.IO)

    private val badPasswordLiveData = MutableLiveData<Boolean>()
    val badPassword : LiveData<Boolean> = badPasswordLiveData

    private val validateLiveData = MutableLiveData<Boolean>()
    val validate : LiveData<Boolean> = validateLiveData

    private val newAccountLiveData = MutableLiveData<String>()
    val newAccount : LiveData<String> = newAccountLiveData

    fun auth(login : String, password: String){
        auth?.cancel()
        auth = coroutineIO.launch {
            val account = checkAccount(login)
            if (account == null){
                saveAccount(login, password)
                newAccountLiveData.postValue(login)
            } else {
                if (account.password == password){
                    sharedPreferencesDelegate.setToken(account.token)
                    validateLiveData.postValue(true)
                } else {
                    badPasswordLiveData.postValue(true)
                }
            }
        }
    }

    override fun onCleared() {
        coroutineIO.cancel()
        super.onCleared()
    }

    private suspend fun checkAccount(login : String) : Account? =
        accountDao.getByLoginPassword(login)

    private suspend fun saveAccount(login : String, password : String){
        val token = generateToken(login, password)
        val account = Account(
            login = login,
            password = password,
            token = token)

        accountDao.insert(account)

        sharedPreferencesDelegate.setToken(token)
    }

    private fun generateToken(login: String, password: String) : String{
        return login.plus(password)
    }
}