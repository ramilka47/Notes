package com.ramil.notes.navigation

import android.content.Intent
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ramil.notes.ui.MainActivity
import com.ramil.notes.ui.dialogs.MenuActionsDialog
import com.ramil.notes.ui.fragments.LoginFragment
import com.ramil.notes.ui.fragments.NoteFragment
import com.ramil.notes.ui.fragments.NotesFragment

object Screens {

    fun main() = ActivityScreen{
        Intent(it, MainActivity::class.java)
    }

    fun login() = FragmentScreen{
        LoginFragment()
    }

    fun notes() = FragmentScreen{
        NotesFragment()
    }

    fun note() = FragmentScreen{
        NoteFragment()
    }

    fun actions(onResult : Fragment) = FragmentScreen{
        MenuActionsDialog().apply {
            setTargetFragment(onResult, MenuActionsDialog.REQUEST_CODE)
        }
    }

}