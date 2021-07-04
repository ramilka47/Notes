package com.ramil.notes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.ramil.notes.Application
import com.ramil.notes.R
import com.ramil.notes.ui.navigation.Screens
import com.ramil.notes.ui.viewmodels.AuthViewModel
import com.ramil.notes.ui.viewmodels.ViewModelFactory
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var router : Router

    private val viewModel by lazy {
        viewModelFactory.create(AuthViewModel::class.java)
    }

    init {
        Application.component.inject(this)
    }

    private lateinit var login : EditText
    private lateinit var password : EditText
    private lateinit var enter : Button
    private lateinit var loginFrame : View
    private lateinit var loading : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view){
            login = findViewById(R.id.login)
            password = findViewById(R.id.password)
            enter = findViewById(R.id.enter)
            enter.setOnClickListener {
                viewModel.auth(login.text.toString(), password.text.toString())
            }

            loginFrame = findViewById(R.id.loginFrame)
            loginFrame.visibility = View.VISIBLE

            loading = findViewById(R.id.loading)
            loading.visibility = View.GONE

            subscribe()
        }
    }

    private fun subscribe(){
        viewModel.badPassword.observe(this, {
            showToast(getString(R.string.badPassword))
        })
        viewModel.newAccount.observe(this, {
            showToast(String.format(getString(R.string.welcome), it))
            main()
        })
        viewModel.validate.observe(this, {
            main()
        })
        viewModel.loading.observe(this, {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        })

        viewModel.uncorrectedLogin.observe(this,{
            showToast(getString(R.string.uncorrectedLogin))
        })
        viewModel.uncorrectedPassword.observe(this, {
            showToast(getString(R.string.uncorrectedPassword))
        })
    }

    private fun showProgress(){
        loginFrame.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        loginFrame.visibility = View.VISIBLE
        loading.visibility = View.GONE
    }

    private fun showToast(string : String){
        Toast.makeText(
            requireContext(),
            string,
            Toast.LENGTH_SHORT).show()
    }

    private fun main(){
        router.newRootScreen(Screens.main())
    }

}