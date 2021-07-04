package com.ramil.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramil.notes.data.entity.Account
import com.ramil.notes.domain.db.dao.AccountDao
import com.ramil.notes.domain.SharedPreferencesDelegate
import kotlinx.coroutines.*

class AuthViewModel(private val accountDao: AccountDao,
                    private val sharedPreferencesDelegate: SharedPreferencesDelegate) : ViewModel() {

    private var authJob : Job? = null
    private val coroutineIO = CoroutineScope(Dispatchers.IO)

    private val badPasswordLiveData = MutableLiveData<Boolean>()
    val badPassword : LiveData<Boolean> = badPasswordLiveData

    private val validateLiveData = MutableLiveData<Boolean>()
    val validate : LiveData<Boolean> = validateLiveData

    private val newAccountLiveData = MutableLiveData<String>()
    val newAccount : LiveData<String> = newAccountLiveData

    private val loadingLiveData = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = loadingLiveData

    private val uncorrectedLoginLiveData = MutableLiveData<Boolean>()
    val uncorrectedLogin : LiveData<Boolean> = uncorrectedLoginLiveData

    private val uncorrectedPasswordLiveData = MutableLiveData<Boolean>()
    val uncorrectedPassword : LiveData<Boolean> = uncorrectedPasswordLiveData

    init {
        sharedPreferencesDelegate.clearToken()
    }

    fun auth(login : String, password: String){
        authJob?.cancel()
        if (login.length < 5){
            uncorrectedLoginLiveData.postValue(true)
            return
        }
        if (password.length < 5){
            uncorrectedPasswordLiveData.postValue(true)
            return
        }

        authJob = coroutineIO.launch {
            loadingLiveData.postValue(true)
            delay(2000)
            val account = checkAccount(login)
            if (account == null){
                saveAccount(login, password)
                newAccountLiveData.postValue(login)
            } else {
                if (account.password == password){
                    sharedPreferencesDelegate.setToken(account.token)
                    validateLiveData.postValue(true)
                } else {
                    badPasswordLiveData.postValueWithResetLoading(true)
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

    private fun <T> MutableLiveData<T>.postValueWithResetLoading(t : T){
        loadingLiveData.postValue(false)
        this.postValue(t)
    }
}