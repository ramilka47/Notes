package com.ramil.notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.ramil.notes.Application
import com.ramil.notes.R
import com.ramil.notes.ui.navigation.Screens
import com.ramil.notes.ui.viewmodels.ActivityViewModel
import com.ramil.notes.ui.viewmodels.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private val navigator : Navigator by lazy {
        AppNavigator(this, R.id.container)
    }

    private val viewModel : ActivityViewModel by lazy{
        viewModelFactory.create(ActivityViewModel::class.java)
    }

    init {
        Application.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribe()
    }

    private fun subscribe(){
        viewModel.main.observe(this, { showMain() })
        viewModel.login.observe(this, { showLogin() })
    }

    private fun showMain(){
        navigator.applyCommands(arrayOf(Forward(Screens.main())))
    }

    private fun showLogin(){
        navigator.applyCommands(arrayOf(Forward(Screens.login())))
    }

    override fun onStart() {
        super.onStart()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        navigator.applyCommands(arrayOf(Back()))
    }

}